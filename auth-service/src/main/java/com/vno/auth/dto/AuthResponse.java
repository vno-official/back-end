package com.vno.auth.dto;

import jakarta.annotation.Nonnull;

public record AuthResponse(
        @Nonnull String accessToken,
        @Nonnull String refreshToken,
        long expiresIn,
        @Nonnull String tokenType) {
    public AuthResponse(String accessToken, String refreshToken, long expiresIn) {
        this(accessToken, refreshToken, expiresIn, "Bearer");
    }
}