// auth-service/src/main/java/com/vno/auth/model/UserSession.java
package com.vno.auth.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "refresh_token_id", nullable = false)
  private RefreshToken refreshToken;

  @Column(name = "user_agent")
  private String userAgent;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "last_activity_at", nullable = false)
  private Instant lastActivityAt;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  private boolean revoked;

  @Column(name = "revoked_at")
  private Instant revokedAt;

  public boolean isExpired() {
    return Instant.now().isAfter(expiresAt);
  }

  public boolean isActive() {
    return !revoked && !isExpired();
  }

  public void updateActivity() {
    this.lastActivityAt = Instant.now();
  }

  public void revoke() {
    this.revoked = true;
    this.revokedAt = Instant.now();
    if (this.refreshToken != null) {
      this.refreshToken.revoke("Session revoked");
    }
  }
}
