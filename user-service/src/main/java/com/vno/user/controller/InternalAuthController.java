package com.vno.user.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.vno.user.model.User;
import com.vno.user.repository.UserRepository;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Hidden
@Validated
@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class InternalAuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/verify")
  public ResponseEntity<UserInfo> verify(@Valid @RequestBody VerifyRequest request) {
    Optional<User> userOpt = userRepository.findByUsername(request.getUsernameOrEmail());
    if (userOpt.isEmpty()) {
      userOpt = userRepository.findByEmail(request.getUsernameOrEmail());
    }
    if (userOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    User user = userOpt.get();
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    UserInfo info = new UserInfo();
    info.setId(user.getId());
    info.setUsername(user.getUsername());
    info.setEmail(user.getEmail());
    return ResponseEntity.ok(info);
  }

  @Data
  public static class VerifyRequest {
    @NotBlank private String usernameOrEmail;
    @NotBlank private String password;
  }

  @Data
  public static class UserInfo {
    private java.util.UUID id;
    private String username;
    private String email;
  }
}
