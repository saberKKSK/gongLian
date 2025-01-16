package com.gonglian.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;
    private static final String TOKEN_PREFIX = "token:";

    /**
     * 存储token
     */
    public void setToken(String username, String token, Long expiration) {
        String key = TOKEN_PREFIX + username;
        log.debug("Storing token for user: {} with key: {}", username, key);
        redisTemplate.opsForValue().set(key, token, expiration, TimeUnit.MILLISECONDS);
    }

    /**
     * 验证token
     */
    public boolean validateToken(String username, String token) {
        String key = TOKEN_PREFIX + username;
        String storedToken = redisTemplate.opsForValue().get(key);
        log.debug("Validating token for user: {}", username);
        log.debug("Stored token: {}", storedToken);
        log.debug("Provided token: {}", token);
        
        if (storedToken == null) {
            log.warn("No token found in Redis for user: {}", username);
            return false;
        }
        
        boolean isValid = token.equals(storedToken);
        if (!isValid) {
            log.warn("Token mismatch for user: {}", username);
        }
        return isValid;
    }

    /**
     * 删除token
     */
    public void deleteToken(String username) {
        String key = TOKEN_PREFIX + username;
        redisTemplate.delete(key);
    }
} 