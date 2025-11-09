dependencies {
    implementation(project(":common"))

    // Spring Cloud Gateway (WebFlux)
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // OpenAPI (WebFlux)
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")

    // Security and Resource Server (JWT validation)
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Circuit Breaker / Resilience4j
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")

    // Config client
    implementation("org.springframework.cloud:spring-cloud-config-client")

    // Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // JWT (JJWT)
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Boot jar configuration
tasks.bootJar {
    archiveFileName.set("api-gateway.jar")
    launchScript()
}