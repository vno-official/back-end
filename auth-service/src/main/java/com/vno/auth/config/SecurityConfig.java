package com.vno.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authz -> authz
                                                // 1. CHO PHÉP OpenAPI & Swagger UI
                                                .requestMatchers(
                                                                "/v3/api-docs/**",
                                                                "/swagger-ui.html",
                                                                "/swagger-ui/**",
                                                                "/webjars/**")
                                                .permitAll()

                                                // 2. API công khai
                                                .requestMatchers(
                                                                "/api/auth/register",
                                                                "/api/auth/test",
                                                                "/api/auth/login",
                                                                "/api/auth/refresh-token",
                                                                "/api/auth/verify-email",
                                                                "/api/auth/resend-verification")
                                                .permitAll()

                                                // 3. Các API khác cần JWT
                                                .anyRequest().authenticated());

                return http.build();
        }

        // Optional: Nếu cần custom JwtDecoder
        // @Bean
        // public JwtDecoder jwtDecoder() {
        // return NimbusJwtDecoder.withJwkSetUri("http://...").build();
        // }
}