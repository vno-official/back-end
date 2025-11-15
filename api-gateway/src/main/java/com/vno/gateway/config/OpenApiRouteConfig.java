// api-gateway/src/main/java/com/vno/gateway/config/OpenApiRouteConfig.java
package com.vno.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiRouteConfig {

    @Bean
    public RouteLocator openApiRoute(RouteLocatorBuilder builder) {
        return builder.routes()

                .route(r -> r.path("/v3/api-docs/auth-service")
                        .filters(f -> f.rewritePath("/v3/api-docs/auth-service", "/v3/api-docs"))
                        .uri("lb://auth-service"))

                .route(r -> r.path("/v3/api-docs/user-service")
                        .filters(f -> f.rewritePath("/v3/api-docs/user-service", "/v3/api-docs"))
                        .uri("lb://user-service"))

                .build();
    }
}