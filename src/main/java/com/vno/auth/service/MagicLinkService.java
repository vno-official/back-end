package com.vno.auth.service;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.UUID;

@ApplicationScoped
public class MagicLinkService {

    private static final String MAGIC_LINK_PREFIX = "magic:";
    private static final Duration TOKEN_TTL = Duration.ofMinutes(15);

    @Inject
    RedisDataSource redisDataSource;

    private ValueCommands<String, String> getCommands() {
        return redisDataSource.value(String.class, String.class);
    }

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        String key = MAGIC_LINK_PREFIX + token;
        getCommands().setex(key, TOKEN_TTL.getSeconds(), email);
        return token;
    }

    public String validateToken(String token) {
        String key = MAGIC_LINK_PREFIX + token;
        String email = getCommands().get(key);
        if (email != null) {
            // Delete token after use (one-time use)
            getCommands().getdel(key);
        }
        return email;
    }
}
