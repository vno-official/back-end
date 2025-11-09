// src/main/java/com/vno/auth/repository/TokenRepository.java
package com.vno.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.vno.auth.model.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
  Optional<Token> findByToken(String token);

  @Query(
      """
        SELECT t FROM Token t
        WHERE t.userId = :userId AND
              t.tokenType = 'REFRESH' AND
              t.revoked = false AND
              t.expired = false AND
              t.expiryDate > CURRENT_TIMESTAMP
    """)
  List<Token> findAllValidTokenByUser(String userId);

  @Transactional
  @Modifying
  @Query(
      """
        UPDATE Token t
        SET t.revoked = true, t.expired = true
        WHERE t.userId = :userId AND
              t.revoked = false AND
              t.expired = false
    """)
  void invalidateAllUserTokens(String userId);

  @Transactional
  @Modifying
  @Query(
      """
        UPDATE Token t
        SET t.revoked = true, t.expired = true
        WHERE t.id = :tokenId
    """)
  void revokeToken(String tokenId);
}
