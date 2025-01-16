package com.gonglian.security;

import com.gonglian.mapper.UserMapper;
import com.gonglian.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("正在加载用户: {}", username);
        Users user = userMapper.findByUsername(username);
        
        if (user == null) {
            log.error("用户 {} 不存在", username);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("用户 {} 加载成功, 角色: {}", username, user.getRole());
        log.debug("密码哈希值: {}", user.getPasswordHash());
        
        // 创建 UserDetails 对象
        UserDetails userDetails = User.builder()
            .username(user.getUsername())
            .password(user.getPasswordHash()) // 确保这里使用的是数据库中的密码哈希
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
            
        log.debug("创建的 UserDetails 密码: {}", userDetails.getPassword());
        return userDetails;
    }
} 