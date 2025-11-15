import org.gradle.api.plugins.JavaPlugin
import org.gradle.jvm.toolchain.JavaLanguageVersion
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    `java-library`
    id("org.springframework.boot") version "3.5.7" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "com.vno"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

// Configure all subprojects
subprojects {
    when {
        project.path == ":common" -> {
            apply(plugin = "java-library")
            
            dependencies {
                "api"("jakarta.validation:jakarta.validation-api:3.0.2")
                "api"("com.fasterxml.jackson.core:jackson-databind:2.15.3")
                "api"("org.slf4j:slf4j-api:2.0.9")
                
                "compileOnly"("org.projectlombok:lombok:1.18.34")
                "annotationProcessor"("org.projectlombok:lombok:1.18.34")
            }
        }
        project.path == ":contracts" -> {
            // Chỉ lưu API specs, không cần build config
        }
        else -> {
            apply(plugin = "java")
            apply(plugin = "io.spring.dependency-management")
            
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

            dependencies {
                "implementation"(platform("org.springframework.boot:spring-boot-dependencies:3.5.7"))
                "implementation"(platform("org.springframework.cloud:spring-cloud-dependencies:2025.0.0"))

                "implementation"("org.springframework.cloud:spring-cloud-starter-openfeign")
                "implementation"("org.springframework.boot:spring-boot-starter-actuator")
                
                "compileOnly"("org.projectlombok:lombok:1.18.34")
                "annotationProcessor"("org.projectlombok:lombok:1.18.34")
            }

            the<DependencyManagementExtension>().imports {
                mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.7")
                mavenBom("org.springframework.cloud:spring-cloud-dependencies:2025.0.0")
            }
        }
    }
}

// Infrastructure modules
configure(listOf(project(":config-server"))) {
    apply(plugin = "org.springframework.boot")
    
    dependencies {
        "implementation"("org.springframework.cloud:spring-cloud-config-server")
    }
}

configure(listOf(project(":service-discovery"))) {
    apply(plugin = "org.springframework.boot")
    
    dependencies {
        "implementation"("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    }
}

// Business services
configure(listOf(
    project(":auth-service"),
    project(":user-service"),
    project(":note-service"),
    project(":tag-service"),
    project(":organization-service"),
    project(":notification-service")
)) {
    apply(plugin = "org.springframework.boot")
    
    dependencies {
        "implementation"("org.springframework.boot:spring-boot-starter-web")
        "implementation"("org.springframework.boot:spring-boot-starter-security")
        "implementation"("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
        "implementation"("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.14")
        "implementation"(project(":common"))
        
        "implementation"("org.mapstruct:mapstruct:1.5.5.Final")
        "annotationProcessor"("org.mapstruct:mapstruct-processor:1.5.5.Final")
    }
}
