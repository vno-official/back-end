package com.vno.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple aggregator that fetches downstream service OpenAPI JSON and merges
 * paths under service prefix.
 * This is a minimal sample implementation for the auth-service only.
 */
@RestController
@RequestMapping("/v3/api-docs")
public class OpenApiAggregatorController {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenApiAggregatorController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
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

        // Fetch auth-service OpenAPI (expects auth-service available at localhost:8081
        // and prefixed path /api/auth)
        try {
            Map authDoc = webClient.get()
                    .uri("http://localhost:8081/api/auth/v3/api-docs")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (authDoc != null) {
                Object authPathsObj = authDoc.get("paths");
                if (authPathsObj instanceof Map) {
                    Map<String, Object> authPaths = (Map<String, Object>) authPathsObj;
                    for (Map.Entry<String, Object> e : authPaths.entrySet()) {
                        // prefix path with /auth to avoid collisions
                        String prefixed = "/auth" + e.getKey();
                        paths.put(prefixed, e.getValue());
                    }
                }

                Object authComponents = authDoc.get("components");
                if (authComponents instanceof Map) {
                    components.putAll((Map) authComponents);
                }
            }
        } catch (Exception ex) {
            // log and continue with empty service
            // In production you'd want better error handling and timeouts
            aggregated.put("x-aggregation-error", "auth-service unreachable: " + ex.getMessage());
        }

        aggregated.put("paths", paths);
        if (!components.isEmpty())
            aggregated.put("components", components);

        return ResponseEntity.ok(aggregated);
    }
}
