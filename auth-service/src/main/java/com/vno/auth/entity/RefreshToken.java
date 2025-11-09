package com.vno.auth.entity;

import java.time.LocalDateTime;

import com.vno.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "LegacyAuthRefreshToken")
@Table(
    name = "auth_refresh_tokens_legacy",
    indexes = {
      @Index(name = "idx_legacy_token", columnList = "token"),
      @Index(name = "idx_legacy_user_id", columnList = "userId")
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private LocalDateTime expiresAt;

  @Column(nullable = false)
  @Builder.Default
  private Boolean revoked = false;

  private LocalDateTime revokedAt;

  @Column(length = 100)
  private String deviceInfo;

  @Column(length = 45)
  private String ipAddress;

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expiresAt);
  }

  public boolean isValid() {
    return !revoked && !isExpired();
  }
}
