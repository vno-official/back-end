package com.vno.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.vno.auth.dto.*;
import com.vno.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and authorization endpoints")
public class AuthController {

  private final AuthService authService;

  @GetMapping("/test")
  @Operation(summary = "Register new user", description = "Creates a new user account and sends verification email")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Registration successful"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "409", description = "User already exists")
  })
  public ResponseEntity<com.vno.common.dto.ApiResponse<String>> getDefault() {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(com.vno.common.dto.ApiResponse.success("ok"));
  }

  @PostMapping("/register")
  @Operation(summary = "Register new user", description = "Creates a new user account and sends verification email")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Registration successful"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "409", description = "User already exists")
  })
  public ResponseEntity<com.vno.common.dto.ApiResponse<AuthResponse>> register(
      @Parameter(description = "User registration data", required = true) @Valid @RequestBody RegisterRequest request) {
    AuthResponse response = authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(com.vno.common.dto.ApiResponse.success(
            "Registration successful. Please check your email to verify your account.",
            response));
  }

  @PostMapping("/verify-email")
  @Operation(summary = "Verify email", description = "Verifies user email with verification code")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Email verified successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid verification code"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<com.vno.common.dto.ApiResponse<Void>> verifyEmail(
      @Parameter(description = "Email verification request", required = true) @Valid @RequestBody VerifyEmailRequest request) {
    authService.verifyEmail(request);
    return ResponseEntity.ok(com.vno.common.dto.ApiResponse.success("Email verified successfully", null));
  }

  @PostMapping("/resend-verification")
  @Operation(summary = "Resend verification code", description = "Resends email verification code")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Verification code sent"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<com.vno.common.dto.ApiResponse<Void>> resendVerification(
      @Parameter(description = "User email address", required = true, example = "user@example.com") @RequestParam String email) {
    authService.resendVerificationCode(email);
    return ResponseEntity.ok(com.vno.common.dto.ApiResponse.success("Verification code sent to your email", null));
  }

  @PostMapping("/login")
  @Operation(summary = "User login", description = "Authenticates user and returns JWT tokens")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Login successful"),
      @ApiResponse(responseCode = "401", description = "Invalid credentials"),
      @ApiResponse(responseCode = "403", description = "Email not verified")
  })
  public ResponseEntity<com.vno.common.dto.ApiResponse<AuthResponse>> login(
      @Parameter(description = "User login credentials", required = true) @Valid @RequestBody LoginRequest request,
      HttpServletRequest httpRequest) {
    AuthResponse response = authService.authenticate(request, httpRequest);
    return ResponseEntity.ok(com.vno.common.dto.ApiResponse.success(response));
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "Refresh access token", description = "Generates new access token using refresh token")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
      @ApiResponse(responseCode = "401", description = "Invalid refresh token")
  })
  public ResponseEntity<com.vno.common.dto.ApiResponse<AuthResponse>> refreshToken(
      @Parameter(description = "Refresh token request", required = true) @Valid @RequestBody RefreshTokenRequest request,
      HttpServletRequest httpRequest) {
    AuthResponse response = authService.refreshToken(request, httpRequest);
    return ResponseEntity.ok(com.vno.common.dto.ApiResponse.success(response));
  }

  @PostMapping("/logout")
  @Operation(summary = "Logout", description = "Revokes the refresh token")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Logged out successfully"),
      @ApiResponse(responseCode = "401", description = "Invalid token")
  })
  public ResponseEntity<com.vno.common.dto.ApiResponse<Void>> logout(
      @Parameter(description = "Refresh token to revoke", required = true) @RequestBody RefreshTokenRequest request) {
    authService.logout(request.getRefreshToken());
    return ResponseEntity.ok(com.vno.common.dto.ApiResponse.success("Logged out successfully", null));
  }
}