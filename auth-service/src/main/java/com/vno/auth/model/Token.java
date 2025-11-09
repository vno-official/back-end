// src/main/java/com/vno/auth/model/Token.java
package com.vno.auth.model;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true)
  private String token;

  @Column(nullable = false)
  private String userId;

  @Enumerated(EnumType.STRING)
  private TokenType tokenType = TokenType.BEARER;

  private boolean revoked;
  private boolean expired;

  @Column(nullable = false)
  private Instant expiryDate;

  private String userAgent;
  private String ipAddress;

  @Column(nullable = false)
  private Instant createdAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = Instant.now();
  }

  public enum TokenType {
    BEARER,
    REFRESH
  }
}
