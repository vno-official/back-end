package com.vno.gateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Authentication")
                .pathsToMatch("/auth/**")
                .build();
    }

    // @Bean
    // public GroupedOpenApi userServiceApi() {
    // return GroupedOpenApi.builder()
    // .group("user")
    // .pathsToMatch("/note/**")
    // .build();
    // }

    // @Bean
    // public GroupedOpenApi noteServiceApi() {
    // return GroupedOpenApi.builder()
    // .group("note")
    // .pathsToMatch("/note/**")
    // .build();
    // }

    // @Bean
    // public GroupedOpenApi tagServiceApi() {
    // return GroupedOpenApi.builder()
    // .group("tag")
    // .pathsToMatch("/note/**")
    // .build();
    // }
}
