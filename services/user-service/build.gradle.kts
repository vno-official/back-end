dependencies {
    implementation(project(":common"))
    
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.security:spring-security-crypto")
    
    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-config-client")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    
    // OpenAPI/Swagger Documentation
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    
    // Database
    implementation("org.flywaydb:flyway-core:10.0.1")
    implementation("org.flywaydb:flyway-database-postgresql:10.0.1")
    runtimeOnly("org.postgresql:postgresql:42.7.0")
    runtimeOnly("com.h2database:h2:2.2.224")
    
    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    
    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.19.3")
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
}

// Configure MapStruct to use Spring's component model
tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs = listOf(
        "-Amapstruct.defaultComponentModel=spring"
    )
}

// Configure Boot jar
tasks.bootJar {
    archiveFileName.set("user-service.jar")
    launchScript()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

springBoot {
    buildInfo()
}