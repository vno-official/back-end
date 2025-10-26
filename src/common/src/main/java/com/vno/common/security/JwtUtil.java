package com.vno.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

  @Value("${jwt.secret:default-test-secret}")
  private String secret;

  @Value("${jwt.access-token-expiration:3600000}") // 1 hour default
  private Long accessTokenExpiration;

  @Value("${jwt.refresh-token-expiration:604800000}") // 7 days default
  private Long refreshTokenExpiration;

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateAccessToken(Long userId, String username, String email) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);
    claims.put("username", username);
    claims.put("email", email);
    claims.put("type", "access");
    return createToken(claims, username, accessTokenExpiration);
  }

  public String generateRefreshToken(Long userId, String username) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);
    claims.put("type", "refresh");
    return createToken(claims, username, refreshTokenExpiration);
  }

  private String createToken(Map<String, Object> claims, String subject, Long expiration) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Long extractUserId(String token) {
    return extractClaim(token, claims -> claims.get("userId", Long.class));
  }

  public String extractEmail(String token) {
    return extractClaim(token, claims -> claims.get("email", String.class));
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public Boolean isTokenExpired(String token) {
    try {
      return extractExpiration(token).before(new Date());
    } catch (ExpiredJwtException e) {
      return true;
    }
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public Long getAccessTokenExpiration() {
    return accessTokenExpiration;
  }

  public Long getRefreshTokenExpiration() {
    return refreshTokenExpiration;
  }
}
