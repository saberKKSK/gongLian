package com.gonglian;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    
    @Test
    public void verifyPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "200152";
        String storedHash = "$2a$10$YMa6GhbOBtbvmY9rrwQIYuIm3YoGqeWU5A4d9oXwJvd0uYwjXJQZi";
        
        // 验证密码
        boolean matches = encoder.matches(rawPassword, storedHash);
        System.out.println("Password matches: " + matches);
        
        // 生成新的密码哈希，以备需要
        String newHash = encoder.encode(rawPassword);
        System.out.println("New hash: " + newHash);
    }

    @Test
    public void generateNewPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "200152";
        String encodedPassword = encoder.encode(password);
        System.out.println("New password hash: " + encodedPassword);
        
        // 验证新生成的密码
        boolean matches = encoder.matches(password, encodedPassword);
        System.out.println("Verification result: " + matches);
    }
} 