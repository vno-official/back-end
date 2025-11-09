package com.vno.auth.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionResponse {
  private UUID id;
  private String userAgent;
  private String ipAddress;
  private Instant lastActivityAt;
  private Instant createdAt;
  private Instant expiresAt;
}
