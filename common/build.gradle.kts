plugins {
    `java-library`
}

dependencies {
    // Using explicit coordinates to avoid version-catalog accessor issues in build script
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2022.0.4"))
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.1.6"))

    // Core Spring Boot starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
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