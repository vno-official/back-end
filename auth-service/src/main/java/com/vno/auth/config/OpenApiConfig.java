package com.vno.auth.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("Authentication Service API")
                        .version("1.0.0")
                        .description("API for user authentication and authorization")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .packagesToScan("com.vno.auth.controller")
                .pathsToMatch("/api/auth/**")
                .build();
    }
        // .contact(new Contact()
        // .name("Auth Service Team")
        // .email("auth-team@company.com"))
        // .license(new License()
        // .name("Apache 2.0")
        // .url("http://springdoc.org")))
        // .servers(List.of(
        // new Server()
        // .url("http://localhost:" + serverPort)
        // .description("Development Server")));
        // }
}
