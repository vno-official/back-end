pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.springframework.boot") version "3.1.6"
        id("io.spring.dependency-management") version "1.1.4"
    }
}

rootProject.name = "vno-backend"

// Core infrastructure modules
include(":common")
include(":config-server")
include(":service-discovery")
include(":contracts")


include(":auth-service")
include(":user-service")
include(":note-service")
include(":tag-service")
include(":organization-service")
include(":notification-service")
include(":api-gateway")