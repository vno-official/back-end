package com.vno.security.jwt;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    @Inject
    JWTParser jwtParser;
    public String generateAccessToken(String email, Set<String> roles) {
        return Jwt.issuer("https://vno.app")
            .subject(email)
            .groups(roles)
            .expiresAt(Instant.now().plusSeconds(3600)) // 1h
            .sign();
    }

    public String generateRefreshToken(String email) {
        return Jwt.issuer("https://vno.app")
            .subject(email)
            .claim("type", "refresh")
            .expiresAt(Instant.now().plusSeconds(30L * 24 * 3600)) // 30 ngày
            .sign();
    }
    public String extractEmailFromRefreshToken(String refreshToken) {
        try {
            return jwtParser.parse(refreshToken).getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid refresh token", e);
        }
    }
}