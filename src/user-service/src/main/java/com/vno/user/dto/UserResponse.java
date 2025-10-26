package com.vno.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vno.user.model.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private UUID id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private UserStatus status;
  private boolean emailVerified;
  private String avatarUrl;
  private String timezone;
  private String locale;
  private LocalDateTime lastLogin;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
