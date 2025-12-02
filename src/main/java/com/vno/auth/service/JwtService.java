package com.vno.auth.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "app.jwt.secret")
    String jwtSecret;

    @ConfigProperty(name = "app.jwt.expiry-hours", defaultValue = "168")
    int expiryHours;

    public String generateToken(Long userId, String email, String name, Long orgId, String role) {
        Set<String> groups = new HashSet<>();
        groups.add(role);

        return Jwt.issuer("vno-backend")
                .subject(userId.toString())
                .claim("email", email)
                .claim("name", name)
                .claim("org_id", orgId)
                .claim("role", role)
                .groups(groups)
                .expiresIn(Duration.ofHours(expiryHours))
                .sign();
    }
}
