package com.gonglian.service;

import com.gonglian.dto.LoginDTO;

public interface AuthService {
    
    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return JWT token
     */
    String login(LoginDTO loginDTO);

    /**
     * 用户退出登录
     *
     * @param token JWT token
     */
    void logout(String token);
} 