// api-gateway/src/main/java/com/vno/gateway/config/OpenApiRouteConfig.java
package com.vno.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiRouteConfig {

    /**
     * Định nghĩa các route ẩn để Gateway gọi đến endpoint /v3/api-docs
     * của từng microservice.
     */
    @Bean
    public RouteLocator openApiRoute(RouteLocatorBuilder builder) {
        return builder.routes()
            // ROUTE ẨN CHO AUTH SERVICE DOC
            // Khi Gateway gọi /v3/api-docs/auth-service, nó sẽ được chuyển tiếp đến auth-service
            .route(r -> r.path("/v3/api-docs/auth-service")
                .filters(f -> f.rewritePath("/v3/api-docs/auth-service", "/v3/api-docs"))
                .uri("lb://auth-service"))
                
            // (Sau này thêm User Service vào đây)
            // .route(r -> r.path("/v3/api-docs/user-service")
            //     .filters(f -> f.rewritePath("/v3/api-docs/user-service", "/v3/api-docs"))
            //     .uri("lb://user-service"))

            .build();
    }
}