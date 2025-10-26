// auth-service/src/main/java/com/vno/auth/task/CleanupExpiredSessionsTask.java
package com.vno.auth.task;

import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vno.auth.repository.UserSessionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanupExpiredSessionsTask {

  private final UserSessionRepository userSessionRepository;

  @Scheduled(fixedRate = 3600000) // Run every hour
  @Transactional
  public void cleanupExpiredSessions() {
    log.info("Cleaning up expired sessions...");
    Instant now = Instant.now();

    int count = userSessionRepository.cleanupExpiredSessions(now);

    log.info("Cleaned up {} expired sessions", count);
  }
}
