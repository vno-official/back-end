// auth-service/src/main/java/com/vno/auth/repository/UserSessionRepository.java
package com.vno.auth.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vno.auth.model.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
  Optional<UserSession> findByRefreshTokenToken(String refreshToken);

  List<UserSession> findAllByUserIdAndRevokedFalseAndExpiresAtAfter(UUID userId, Instant now);

  @Modifying
  @Transactional
  @Query("UPDATE UserSession us SET us.revoked = true, us.revokedAt = :revokedAt WHERE us.id = :id")
  void revokeSession(UUID id, Instant revokedAt);

  @Modifying
  @Transactional
  @Query(
      "UPDATE UserSession us SET us.revoked = true, us.revokedAt = :revokedAt WHERE us.userId = :userId AND us.revoked = false")
  void revokeAllUserSessions(UUID userId, Instant revokedAt);

  @Modifying
  @Transactional
  @Query("UPDATE UserSession us SET us.lastActivityAt = :lastActivityAt WHERE us.id = :id")
  void updateLastActivity(UUID id, Instant lastActivityAt);

  @Query(
      "SELECT us FROM UserSession us WHERE us.lastActivityAt < :inactiveSince AND us.revoked = false")
  List<UserSession> findInactiveSessions(Instant inactiveSince);

  @Modifying
  @Query(
      "UPDATE UserSession us SET us.revoked = true, us.revokedAt = :now "
          + "WHERE us.expiresAt <= :now AND us.revoked = false")
  int cleanupExpiredSessions(Instant now);
}
