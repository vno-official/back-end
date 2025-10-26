package com.vno.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service
                .route("auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.rewritePath("/api/auth/(?<segment>.*)", "/${segment}"))
                        .uri("lb://auth-service"))
                
                // User Service
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.rewritePath("/api/users/(?<segment>.*)", "/${segment}"))
                        .uri("lb://user-service"))
                
                // Organization Service
                .route("organization-service", r -> r.path("/api/organizations/**")
                        .filters(f -> f.rewritePath("/api/organizations/(?<segment>.*)", "/${segment}"))
                        .uri("lb://organization-service"))
                
                // Note Service
                .route("note-service", r -> r.path("/api/notes/**")
                        .filters(f -> f.rewritePath("/api/notes/(?<segment>.*)", "/${segment}"))
                        .uri("lb://note-service"))
                
                // Tag Service
                .route("tag-service", r -> r.path("/api/tags/**")
                        .filters(f -> f.rewritePath("/api/tags/(?<segment>.*)", "/${segment}"))
                        .uri("lb://tag-service"))
                
                // Notification Service
                .route("notification-service", r -> r.path("/api/notifications/**", "/ws/notifications")
                        .filters(f -> f.rewritePath("/api/notifications/(?<segment>.*)", "/${segment}"))
                        .uri("lb://notification-service"))
                
                // Public endpoints
                .route("auth-service-public", r -> r.path("/api/public/**")
                        .filters(f -> f.rewritePath("/api/public/(?<segment>.*)", "/${segment}"))
                        .uri("lb://auth-service"))
                
                .build();
    }
}
