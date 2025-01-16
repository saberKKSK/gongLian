package com.gonglian.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    @Qualifier("customStringRedisTemplate")
    private final RedisTemplate<String, String> stringRedisTemplate;
    
    @Qualifier("jsonRedisTemplate")
    private final RedisTemplate<String, Object> jsonRedisTemplate;
    
    private final ObjectMapper objectMapper;
    private static final String TOKEN_PREFIX = "token:";

    /**
     * 存储token
     */
    public void setToken(String username, String token, Long expiration) {
        String key = TOKEN_PREFIX + username;
        log.debug("Storing token for user: {} with key: {}", username, key);
        stringRedisTemplate.opsForValue().set(key, token, expiration, TimeUnit.MILLISECONDS);
    }

    /**
     * 验证token
     */
    public boolean validateToken(String username, String token) {
        String key = TOKEN_PREFIX + username;
        String storedToken = stringRedisTemplate.opsForValue().get(key);
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
        stringRedisTemplate.delete(key);
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        try {
            Object value = jsonRedisTemplate.opsForValue().get(key);
            if (value == null) {
                return null;
            }
            
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return objectMapper.convertValue(value, type);
        } catch (Exception e) {
            log.error("Failed to get list from redis", e);
            return null;
        }
    }

    public void setList(String key, Object value, long time) {
        try {
            if (time > 0) {
                jsonRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                jsonRedisTemplate.opsForValue().set(key, value);
            }
        } catch (Exception e) {
            log.error("Failed to set list to redis", e);
        }
    }

    public void delete(String key) {
        try {
            jsonRedisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Failed to delete key from redis", e);
        }
    }
} 