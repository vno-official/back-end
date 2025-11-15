package com.vno.gateway.controller;

// @RestController
// @RequestMapping("/v3/api-docs")
// @RequiredArgsConstructor
public class OpenApiAggregatorController {

    // record ServiceDoc(String name, String serviceId, String docPath, String
    // prefix) {
    // }

    // record Prefix(String prefix) {
    // }

    // private final WebClient.Builder webClientBuilder;

    // private final List<ServiceDoc> services = List.of(
    // new ServiceDoc("auth", "auth-service", "/v3/api-docs/auth", "/api/auth"),
    // new ServiceDoc("user", "user-service", "/v3/api-docs/user", "/api/user"),
    // new ServiceDoc("org", "organization-service", "/v3/api-docs/org",
    // "/api/org"));

    // @GetMapping("/aggregated")
    // public Map<String, Object> getAggregated() {
    // Map<String, Object> result = initResult();

    // services.stream().forEach(service -> { // ← CHẮC CHẮN .stream()
    // try {
    // String url = "lb://" + service.serviceId() + service.docPath();
    // String yaml = webClientBuilder.build()
    // .get()
    // .uri(url)
    // .accept(MediaType.APPLICATION_JSON)
    // .retrieve()
    // .bodyToMono(String.class)
    // .block(Duration.ofSeconds(15));

    // if (yaml != null && !yaml.trim().isEmpty()) {
    // Map<String, Object> doc = Yaml.mapper().readValue(yaml, Map.class);
    // mergeDoc(result, doc, service.prefix());
    // }
    // } catch (Exception e) {
    // getErrorList(result).add(Map.of(
    // "service", service.name(),
    // "error", e.getMessage()));
    // }
    // });

    // return result;
    // }

    // private Map<String, Object> initResult() {
    // Map<String, Object> result = new HashMap<>();
    // result.put("openapi", "3.0.1");
    // result.put("info", Map.of("title", "VNO Aggregated API", "version", "1.0"));
    // result.put("servers", List.of(Map.of("url", "/")));
    // result.put("paths", new HashMap<>());
    // result.put("components", Map.of("schemas", new HashMap<>()));
    // return result;
    // }

    // @SuppressWarnings("unchecked")
    // private List<Map<String, Object>> getErrorList(Map<String, Object> result) {
    // return (List<Map<String, Object>>) result.computeIfAbsent("x-errors", k ->
    // new ArrayList<>());
    // }

    // private void mergeDoc(Map<String, Object> target, Map<String, Object> source,
    // String prefix) {
    // mergePaths(target, source, prefix);
    // // mergeComponents(target, source);
    // }

    // @SuppressWarnings("unchecked")
    // private void mergePaths(Map<String, Object> target, Map<String, Object>
    // source, String prefix) {
    // Map<String, Object> targetPaths = (Map<String, Object>) target.get("paths");
    // Map<String, Object> sourcePaths = (Map<String, Object>) source.get("paths");

    // if (sourcePaths != null) {
    // sourcePaths.forEach((path, ops) -> {
    // String newPath = prefix + path;
    // targetPaths.merge(newPath, ops, (existing, incoming) -> {
    // if (existing instanceof Map && incoming instanceof Map) {
    // ((Map<String, Object>) existing).putAll((Map<String, Object>) incoming);
    // }
    // return existing;
    // });
    // });
    // }
    // }

    // @SuppressWarnings("unchecked")
    // private void mergeComponents(Map<String, Object> target, Map<String, Object>
    // source) {
    // Map<String, Object> targetSchemas = (Map<String, Object>) ((Map<String,
    // Object>) target.get("components"))
    // .get("schemas");
    // Map<String, Object> sourceSchemas = (Map<String, Object>) ((Map<String,
    // Object>) source.get("components"))
    // .get("schemas");

    // if (sourceSchemas != null) {
    // sourceSchemas.forEach((name, schema) -> {
    // String prefixedName = name + "_" + UUID.randomUUID().toString().substring(0,
    // 8);
    // targetSchemas.put(prefixedName, schema);
    // });
    // }
    // }

}