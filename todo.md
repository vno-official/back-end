
## `todo.md` – **Checklist: Tích hợp & Tổng hợp OpenAPI Documentation (Scalable & Production-Grade)**

```md
# TODO: OpenAPI Aggregation & Standardization – Production-Ready Checklist
> **Mục tiêu**: Tất cả microservices sinh OpenAPI chuẩn, API Gateway tự động tổng hợp, dễ scale, không lệch contract.  
> **Công cụ**: `springdoc-openapi`, Spring Cloud Eureka, Contract-First (OpenAPI Generator), API Gateway.  
> **Agent thực thi**: Mỗi task có mô tả chi tiết, file path, code mẫu, lệnh kiểm tra.

---

## [ ] 1. Chuẩn hóa `OpenApiConfig.java` cho **TẤT CẢ** microservices
> **Áp dụng cho**: `auth-service`, `user-service`, `organization-service`, `tag-service`, `note-service`, `notification-service`

```java
// src/main/java/com/vno/{service}/config/OpenApiConfig.java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${spring.application.name}") String appName) {
        String title = capitalize(appName.replace("-", " ")) + " API";
        return new OpenAPI()
            .info(new Info()
                .title(title)
                .version("1.0.0")
                .description("API documentation for " + title))
            .servers(List.of(
                new Server().url("http://localhost:{port}").description("Local Dev"),
                new Server().url("/" + appName).description("Via API Gateway")
            ));
    }

    @Bean
    public GroupedOpenApi publicApi(@Value("${spring.application.name}") String appName) {
        return GroupedOpenApi.builder()
            .group(appName) // VD: user-service
            .pathsToMatch("/api/**")
            .build();
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
```

- [ ] **Tạo/Cập nhật file** `OpenApiConfig.java` trong **mỗi service** theo mẫu trên.
- [ ] **Thay `{port}`** bằng port thực tế trong `application.yml` (VD: `8081` cho user-service).
- [ ] **Kiểm tra**: Khởi động service → truy cập `http://localhost:8081/v3/api-docs` → thấy JSON hợp lệ + `info.title` đúng.

---

## [ ] 2. Đảm bảo **mỗi service expose `/v3/api-docs`** và `/v3/api-docs/{group}`

```yaml
# src/main/resources/application.yml (mỗi service)
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
```

- [ ] **Thêm config trên** vào `application.yml` **tất cả service**.
- [ ] **Kiểm tra**: `GET /v3/api-docs/user-service` → trả về OpenAPI JSON.

---

## [ ] 3. Cập nhật `api-gateway` – **Tự động tổng hợp OpenAPI từ Service Discovery**

```java
// api-gateway/src/main/java/com/vno/gateway/controller/OpenApiAggregatorController.java
@RestController
@RequiredArgsConstructor
@Slf4j
public class OpenApiAggregatorController {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/v3/api-docs")
    public ResponseEntity<Map<String, Object>> getAggregatedApiDocs() throws JsonProcessingException {
        Map<String, Object> aggregated = new LinkedHashMap<>();
        List<String> serviceIds = discoveryClient.getServices()
            .stream()
            .filter(id -> !id.equals("api-gateway") && !id.equals("config-server"))
            .toList();

        for (String serviceId : serviceIds) {
            try {
                InstanceInfo instance = discoveryClient.getInstances(serviceId).get(0);
                String url = instance.getHomePageUrl() + "v3/api-docs/" + serviceId;
                String json = restTemplate.getForObject(url, String.class);

                Map<String, Object> doc = mapper.readValue(json, new TypeReference<>() {});
                String groupName = serviceId + "-api";
                aggregated.put(groupName, doc);

                log.info("Loaded OpenAPI for service: {}", serviceId);
            } catch (Exception e) {
                log.warn("Failed to load OpenAPI for {}: {}", serviceId, e.getMessage());
            }
        }
        return ResponseEntity.ok(aggregated);
    }
}
```

- [ ] **Tạo/Cập nhật controller** theo code trên.
- [ ] **Thêm dependency**:
  ```kotlin
  implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
  implementation 'com.fasterxml.jackson.core:jackson-databind'
  ```
- [ ] **Kiểm tra**: Khởi động tất cả → `GET http://localhost:8080/v3/api-docs` → thấy danh sách các group.

---

## [ ] 4. Cấu hình **Swagger UI tổng hợp** tại API Gateway

```yaml
# api-gateway/src/main/resources/application.yml
springdoc:
  swagger-ui:
    path: /swagger-ui.html
    urls: 
      - name: Auth Service
        url: /v3/api-docs/auth-service-api
      - name: User Service
        url: /v3/api-docs/user-service-api
      - name: Organization Service
        url: /v3/api-docs/organization-service-api
      # ... thêm các service khác
```

- [ ] **Tạo danh sách `urls`** động (tùy chọn nâng cao: dùng `@ConfigurationProperties`).
- [ ] **Kiểm tra**: Truy cập `http://localhost:8080/swagger-ui.html` → thấy dropdown chọn service.

---

## [ ] 5. **Contract-First**: Tích hợp OpenAPI Generator cho **tất cả service**

```kotlin
// build.gradle.kts (mỗi service)
plugins {
    id("org.openapi.generator") version "7.8.0"
}

val openApiFile = when (project.name) {
    "user-service" -> "user-service.yaml"
    "auth-service" -> "auth-service.yaml"
    else -> "${project.name}.yaml"
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/contracts/openapi/$openApiFile")
    outputDir.set("$buildDir/generated")
    apiPackage.set("com.vno.${project.name.replace("-service", "")}.api")
    modelPackage.set("com.vno.${project.name.replace("-service", "")}.model")
    configOptions.set(mapOf(
        "interfaceOnly" to "true",
        "useSpringBoot3" to "true",
        "useTags" to "true"
    ))
}

sourceSets.main {
    java.srcDir("$buildDir/generated/src/main/java")
}

tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
}
```

- [ ] **Tạo file contract** trong `contracts/openapi/` nếu chưa có.
- [ ] **Thêm plugin + task** vào **tất cả service**.
- [ ] **Kiểm tra**: `./gradlew openApiGenerate` → sinh code → không lỗi.

---

## [ ] 6. Cập nhật `docker-compose.yml` & `run-all.bat` – Đảm bảo thứ tự khởi động

```yaml
# service-discovery/docker-compose.yml
services:
  config-server:
    depends_on: []
  service-discovery:
    depends_on:
      - config-server
  api-gateway:
    depends_on:
      - service-discovery
  auth-service:
    depends_on:
      - service-discovery
  user-service:
    depends_on:
      - service-discovery
  # ... các service khác
```

- [ ] **Cập nhật `depends_on`** đúng thứ tự.
- [ ] **Kiểm tra**: `docker-compose up` → tất cả service đăng ký vào Eureka → API Gateway fetch được docs.

---

## [ ] 7. Kiểm tra cuối cùng (End-to-End)

- [ ] Khởi động toàn bộ hệ thống (`run-all.bat` hoặc `docker-compose up`).
- [ ] Truy cập:  
  → `http://localhost:8761` (Eureka) → thấy tất cả service.  
  → `http://localhost:8080/swagger-ui.html` → thấy dropdown đầy đủ.  
  → `http://localhost:8080/v3/api-docs` → JSON tổng hợp hợp lệ.
- [ ] Test 1 service down → aggregator vẫn hoạt động (log warning).

---

## [ ] 8. (Tùy chọn) Cache OpenAPI docs bằng Redis

```java
@Cacheable(value = "openapi", key = "#serviceId")
public Map<String, Object> fetchServiceDocs(String serviceId) { ... }
```

- [ ] Chỉ bật khi scale > 20 services.

---

## Hoàn thành khi:
- [ ] Tất cả `[ ]` được check.
- [ ] `GET /v3/api-docs` từ Gateway trả về JSON hợp lệ.
- [ ] Swagger UI hiển thị đầy đủ.
- [ ] Contract sinh code tự động.

> **Agent**: Sau mỗi task, commit với message: `chore: openapi standardization - {service-name}`
```

---

**File đính kèm**: `todo.md` → copy vào root project.

**Agent có thể tự động**:
- Tạo file `OpenApiConfig.java` theo template.
- Cập nhật `application.yml`.
- Chạy `./gradlew openApiGenerate`.
- Kiểm tra HTTP endpoints.

---

**Bạn muốn tôi sinh luôn file `todo.md` dưới dạng text để copy-paste không?**  
Hoặc **tạo script tự động hóa 80% checklist này bằng Python/Shell**?