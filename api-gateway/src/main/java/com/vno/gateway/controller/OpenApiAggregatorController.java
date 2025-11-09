package com.vno.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

/**
 * Simple aggregator that fetches downstream service OpenAPI JSON and merges
 * paths under service prefix.
 * This is a minimal sample implementation for the auth-service only.
 */
@RestController
@RequestMapping("/v3/api-docs")
public class OpenApiAggregatorController {

    private static final Logger log = LoggerFactory.getLogger(OpenApiAggregatorController.class);

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DiscoveryClient discoveryClient;

    public OpenApiAggregatorController(WebClient.Builder webClientBuilder, DiscoveryClient discoveryClient) {
        this.webClient = webClientBuilder.build();
        this.discoveryClient = discoveryClient;
    }

    @GetMapping(value = "/aggregated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> aggregated() {
        // Create base aggregated OpenAPI structure
        Map<String, Object> aggregated = new LinkedHashMap<>();
        aggregated.put("openapi", "3.0.1");
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("title", "Aggregated API");
        info.put("version", "1.0.0");
        aggregated.put("info", info);

        Map<String, Object> paths = new LinkedHashMap<>();
        Map<String, Object> components = new LinkedHashMap<>();

        // Discover services via DiscoveryClient and fetch their OpenAPI docs.
        List<String> services = discoveryClient.getServices();
        String selfApp = Optional.ofNullable(System.getProperty("spring.application.name")).orElse("api-gateway");

        for (String serviceId : services) {
            if (serviceId.equalsIgnoreCase(selfApp))
                continue; // skip gateway itself

            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            if (instances == null || instances.isEmpty())
                continue;

            // use first instance
            String baseUri = instances.get(0).getUri().toString();
            String prefix = serviceId.replaceAll("-service$", "").replace('_', '-');
            if (prefix.startsWith("/"))
                prefix = prefix.substring(1);
            prefix = "/" + prefix;

            // try a few candidate paths for OpenAPI docs
            List<String> candidates = Arrays.asList(
                    baseUri + "/v3/api-docs",
                    baseUri + "/api" + prefix + "/v3/api-docs",
                    baseUri + prefix + "/v3/api-docs",
                    baseUri + "/api/v3/api-docs");

            boolean fetched = false;
            for (String url : candidates) {
                try {
                    Map doc = webClient.get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block();

                    if (doc == null)
                        continue;

                    Object pathsObj = doc.get("paths");
                    if (pathsObj instanceof Map) {
                        Map<String, Object> servicePaths = (Map<String, Object>) pathsObj;
                        for (Map.Entry<String, Object> e : servicePaths.entrySet()) {
                            String newPath = prefix + e.getKey();
                            paths.put(newPath, e.getValue());
                        }
                    }

                    Object compObj = doc.get("components");
                    if (compObj instanceof Map) {
                        // shallow merge components - prefer existing entries
                        Map<String, Object> compMap = (Map<String, Object>) compObj;
                        for (Map.Entry<String, Object> c : compMap.entrySet()) {
                            Object existing = components.get(c.getKey());
                            if (existing instanceof Map && c.getValue() instanceof Map) {
                                Map merged = new LinkedHashMap<>((Map) existing);
                                merged.putAll((Map) c.getValue());
                                components.put(c.getKey(), merged);
                            } else if (!components.containsKey(c.getKey())) {
                                components.put(c.getKey(), c.getValue());
                            }
                        }
                    }

                    fetched = true;
                    break;
                } catch (Exception ex) {
                    log.debug("Failed to fetch OpenAPI from {} (serviceId {}). Trying next candidate. error={}", url,
                            serviceId, ex.getMessage());
                }
            }

            if (!fetched) {
                log.warn("Could not fetch OpenAPI doc for service {} at {}", serviceId, baseUri);
            }
        }

        aggregated.put("paths", paths);
        if (!components.isEmpty())
            aggregated.put("components", components);

        return ResponseEntity.ok(aggregated);
    }
}
