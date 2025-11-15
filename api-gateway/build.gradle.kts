plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":common"))

     
    // Spring Cloud Gateway (dựa trên WebFlux, không dùng starter-web để tránh xung đột)
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    
    // Security & OAuth2 Resource Server (JWT validation)
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    
    // Circuit Breaker với Resilience4j
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
    
    // Config client cho cấu hình tập trung
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    
    // JWT lib
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
    
    // Optional: OpenAPI cho WebFlux Gateway (bạn có thể bỏ dòng comment nếu cần)
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.14")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

// Boot jar configuration
tasks.bootJar {
    archiveFileName.set("api-gateway.jar")
    launchScript()
}
