// auth-service/src/main/java/com/vno/auth/model/RefreshToken.java
package com.vno.auth.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "user_agent")
  private String userAgent;

  @Column(name = "ip_address")
  private String ipAddress;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  private boolean revoked;

  @Column(name = "revoked_at")
  private Instant revokedAt;

  @Column(name = "replaced_by_token")
  private String replacedByToken;

  public boolean isExpired() {
    return Instant.now().isAfter(expiresAt);
  }

  public boolean isActive() {
    return !revoked && !isExpired();
  }

  public void revoke(String replacedByToken) {
    this.revoked = true;
    this.revokedAt = Instant.now();
    this.replacedByToken = replacedByToken;
  }
}
