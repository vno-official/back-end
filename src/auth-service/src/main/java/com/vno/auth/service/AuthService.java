package com.vno.auth.service;

import java.util.List;
import java.util.UUID;

import com.vno.auth.dto.*;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
  AuthResponse register(RegisterRequest request);

  AuthResponse authenticate(LoginRequest request, HttpServletRequest httpRequest);

  AuthResponse refreshToken(RefreshTokenRequest request, HttpServletRequest httpRequest);

  void logout(String refreshToken);

  void logoutAll(UUID userId);

  List<UserSessionResponse> getUserSessions(UUID userId);

  void revokeSession(UUID sessionId, UUID userId);

  void verifyEmail(VerifyEmailRequest request);

  void resendVerificationCode(String email);
}
