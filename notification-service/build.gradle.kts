dependencies {
    implementation(project(":common"))

    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // Ably
    implementation("io.ably:ably-java:1.2.5")

    // Database
    runtimeOnly("org.postgresql:postgresql:42.7.0")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Configure JAR file name
tasks.bootJar {
    archiveFileName.set("notification-service.jar")
    launchScript()
}