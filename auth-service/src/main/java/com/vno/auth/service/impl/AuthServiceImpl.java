package com.vno.auth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.System;
import org.springframework.stereotype.Service;

import com.vno.auth.dto.*;
import com.vno.auth.security.JwtService;
import com.vno.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final JwtService jwtService;
  private final com.vno.auth.client.UserServiceClient userClient;
  private final com.vno.auth.repository.RefreshTokenRepository refreshTokenRepository;
  private final com.vno.auth.repository.UserSessionRepository userSessionRepository;

  @org.springframework.beans.factory.annotation.Value("${jwt.refresh-token.expiration}")
  private long refreshTokenExpirationMs;

  @Override
  public AuthResponse register(RegisterRequest request) {
    // Delegate user creation to user-service via public API
    // Build minimal payload
    var createReq = new java.util.HashMap<String, Object>();
    createReq.put("username", request.getUsername());
    createReq.put("email", request.getEmail());
    createReq.put("password", request.getPassword());
    createReq.put("firstName", request.getFirstName());
    createReq.put("lastName", request.getLastName());
    // Create user in user-service
    var cu = new com.vno.auth.client.UserServiceClient.CreateUserRequest();
    cu.setUsername(request.getUsername());
    cu.setEmail(request.getEmail());
    cu.setPassword(request.getPassword());
    cu.setFirstName(request.getFirstName());
    cu.setLastName(request.getLastName());
    var created = userClient.createUser(cu);

    log.info("Created user response: {}", created);
    String subject = created.getData() != null && created.getData().getId() != null
        ? created.getData().getId().toString()
        : request.getUsername();
    String access = jwtService.generateAccessToken(subject);
    String refresh = createAndStoreRefresh(subject, null, null);
    return AuthResponse.builder()
        .accessToken(access)
        .refreshToken(refresh)
        .tokenType("Bearer")
        .expiresIn(3600L)
        .user(
            AuthResponse.UserInfo.builder()
                .id(created.getData() != null ? null : null)
                .email(request.getEmail())
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status("PENDING_VERIFICATION")
                .build())
        .build();
  }

  @Override
  public AuthResponse authenticate(LoginRequest request, HttpServletRequest httpRequest) {
    // Verify credentials via user-service
    var verifyReq = new com.vno.auth.client.UserServiceClient.VerifyRequest();
    verifyReq.setUsernameOrEmail(request.getUsernameOrEmail());
    verifyReq.setPassword(request.getPassword());
    var user = userClient.verify(verifyReq);
    String subject = user.getId() != null ? user.getId().toString() : user.getUsername();
    String access = jwtService.generateAccessToken(subject);
    String ua = httpRequest.getHeader("User-Agent");
    String ip = httpRequest.getRemoteAddr();
    String refresh = createAndStoreRefresh(subject, ua, ip);
    return AuthResponse.builder()
        .accessToken(access)
        .refreshToken(refresh)
        .tokenType("Bearer")
        .expiresIn(3600L)
        .build();
  }

  @Override
  public AuthResponse refreshToken(RefreshTokenRequest request, HttpServletRequest httpRequest) {
    var rtOpt = refreshTokenRepository.findByToken(request.getRefreshToken());
    if (rtOpt.isEmpty() || !rtOpt.get().isActive()) {
      throw new IllegalArgumentException("Invalid refresh token");
    }
    var rt = rtOpt.get();
    userSessionRepository
        .findByRefreshTokenToken(rt.getToken())
        .ifPresent(
            s -> {
              s.updateActivity();
              userSessionRepository.save(s);
            });
    String access = jwtService.generateAccessToken(rt.getUserId().toString());
    return AuthResponse.builder()
        .accessToken(access)
        .refreshToken(rt.getToken())
        .tokenType("Bearer")
        .expiresIn(3600L)
        .build();
  }

  @Override
  public void logout(String refreshToken) {
    refreshTokenRepository
        .findByToken(refreshToken)
        .ifPresent(
            rt -> {
              rt.revoke(null);
              refreshTokenRepository.save(rt);
              userSessionRepository
                  .findByRefreshTokenToken(refreshToken)
                  .ifPresent(
                      s -> {
                        s.revoke();
                        userSessionRepository.save(s);
                      });
            });
  }

  @Override
  public void logoutAll(UUID userId) {
    var now = java.time.Instant.now();
    refreshTokenRepository.revokeAllUserTokens(userId, now);
    userSessionRepository.revokeAllUserSessions(userId, now);
  }

  @Override
  public List<UserSessionResponse> getUserSessions(UUID userId) {
    var sessions = userSessionRepository.findAllByUserIdAndRevokedFalseAndExpiresAtAfter(
        userId, java.time.Instant.now());
    List<UserSessionResponse> result = new ArrayList<>();
    for (var s : sessions) {
      result.add(
          UserSessionResponse.builder()
              .id(s.getId())
              .userAgent(s.getUserAgent())
              .ipAddress(s.getIpAddress())
              .lastActivityAt(s.getLastActivityAt())
              .createdAt(s.getCreatedAt())
              .expiresAt(s.getExpiresAt())
              .build());
    }
    return result;
  }

  @Override
  public void revokeSession(UUID sessionId, UUID userId) {
    userSessionRepository
        .findById(sessionId)
        .filter(s -> s.getUserId().equals(userId))
        .ifPresent(
            s -> {
              s.revoke();
              userSessionRepository.save(s);
              if (s.getRefreshToken() != null) {
                s.getRefreshToken().revoke("Session revoked");
                refreshTokenRepository.save(s.getRefreshToken());
              }
            });
  }

  @Override
  public void verifyEmail(VerifyEmailRequest request) {
    // no-op for demo
  }

  @Override
  public void resendVerificationCode(String email) {
    // no-op for demo
  }

  private String createAndStoreRefresh(String subject, String userAgent, String ip) {
    java.util.UUID userId;
    try {
      userId = java.util.UUID.fromString(subject);
    } catch (IllegalArgumentException ex) {
      // fallback: derive a name-based UUID for non-UUID subject
      userId = java.util.UUID.nameUUIDFromBytes(
          subject.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
    var rt = com.vno.auth.model.RefreshToken.builder()
        .token(java.util.UUID.randomUUID().toString())
        .userId(userId)
        .userAgent(userAgent)
        .ipAddress(ip)
        .expiresAt(java.time.Instant.now().plusMillis(refreshTokenExpirationMs))
        .revoked(false)
        .build();
    rt = refreshTokenRepository.save(rt);
    var session = com.vno.auth.model.UserSession.builder()
        .userId(userId)
        .refreshToken(rt)
        .userAgent(userAgent)
        .ipAddress(ip)
        .lastActivityAt(java.time.Instant.now())
        .expiresAt(rt.getExpiresAt())
        .revoked(false)
        .build();
    userSessionRepository.save(session);
    return rt.getToken();
  }
}
