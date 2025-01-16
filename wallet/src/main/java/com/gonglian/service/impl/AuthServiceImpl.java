package com.gonglian.service.impl;

import com.gonglian.dto.LoginDTO;
import com.gonglian.exception.BusinessException;
import com.gonglian.mapper.UserMapper;
import com.gonglian.model.Users;
import com.gonglian.service.AuthService;
import com.gonglian.utils.JwtUtil;
import com.gonglian.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper  userMapper;

    @Override
    public String login(LoginDTO loginDTO) {
        try {
            log.info("尝试登录用户: {}", loginDTO.getUsername());
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
                )
            );
            
            Users user = userMapper.findByUsername(loginDTO.getUsername());
            String token = jwtUtil.generateToken(authentication.getName(), user.getUserId());
            
            // 存储token到Redis
            log.debug("Storing token in Redis for user: {}", loginDTO.getUsername());
            redisUtil.setToken(authentication.getName(), token, jwtUtil.getExpiration());
            
            return token;
        } catch (AuthenticationException e) {
            log.error("用户 {} 认证失败: {}", loginDTO.getUsername(), e.getMessage(), e);
            throw new BusinessException("用户名或密码错误");
        }
    }
} 