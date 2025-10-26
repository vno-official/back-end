package com.vno.gateway.filter;

import com.vno.common.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private final JwtUtil jwtUtil;

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final List<String> WHITELIST = Arrays.asList(
            "/api/auth/**",
            "/auth/**",
            "/api/public/**",
            "/public/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/actuator/**",
            "/ws/**",
            "/api/auth/v3/api-docs/**",
            "/api/auth/swagger-ui/**",
            "/api/auth/swagger-ui.html",
            "/api/auth/swagger-resources/**",
            "/api/auth/webjars/**",
            "/api/auth/configuration/ui",
            "/api/auth/configuration/security"

    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // Skip auth for whitelisted paths
        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }

        List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null || authHeaders.isEmpty()) {
            return unauthorized(exchange, "Missing Authorization header");
        }

        String authHeader = authHeaders.get(0);
        if (!authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return unauthorized(exchange, "Invalid or expired token");
        }

        // Propagate user context headers downstream
        Long userId = null;
        String username = null;
        try {
            userId = jwtUtil.extractUserId(token);
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            log.debug("Failed to extract claims: {}", e.getMessage());
        }

        ServerHttpRequest mutated = exchange.getRequest().mutate()
                .header("X-User-Id", userId != null ? String.valueOf(userId) : "")
                .header("X-Username", username != null ? username : "")
                .build();

        return chain.filter(exchange.mutate().request(mutated).build());
    }

    private boolean isWhitelisted(String path) {
        for (String pattern : WHITELIST) {
            if (PATH_MATCHER.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBufferFactory bufferFactory = response.bufferFactory();
        String body = String.format("{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"%s\"}", message);
        return response.writeWith(Mono.just(bufferFactory.wrap(body.getBytes(StandardCharsets.UTF_8))));
    }

    @Override
    public int getOrder() {
        // Run early, before most other filters
        return -100;
    }
}
