// auth-service/src/main/java/com/vno/auth/repository/RefreshTokenRepository.java
package com.vno.auth.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vno.auth.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
  Optional<RefreshToken> findByToken(String token);

  List<RefreshToken> findAllByUserIdAndRevokedFalseAndExpiresAtAfter(UUID userId, Instant now);

  @Modifying
  @Query(
      "UPDATE RefreshToken rt SET rt.revoked = true, rt.revokedAt = :revokedAt, rt.replacedByToken = :replacedByToken WHERE rt.id = :id")
  void revokeToken(UUID id, Instant revokedAt, String replacedByToken);

  @Modifying
  @Query(
      "UPDATE RefreshToken rt SET rt.revoked = true, rt.revokedAt = :revokedAt WHERE rt.userId = :userId AND rt.revoked = false")
  void revokeAllUserTokens(UUID userId, Instant revokedAt);

  @Query(
      "SELECT rt FROM RefreshToken rt WHERE rt.userId = :userId AND rt.revoked = false AND rt.expiresAt > :now")
  List<RefreshToken> findActiveTokensByUser(UUID userId, Instant now);
}
