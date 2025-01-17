package com.gonglian.security;

import com.gonglian.mapper.UserMapper;
import com.gonglian.model.Users;
import com.gonglian.utils.JwtUtil;
import com.gonglian.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            RedisUtil redisUtil,
            UserDetailsService userDetailsService,
            UserMapper userMapper) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.debug("Processing request for URI: {}", requestURI);
        
        try {
            final String authHeader = request.getHeader("Authorization");
            log.debug("Auth header: {}", authHeader);
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                final String jwt = authHeader.substring(7);
                final String username = jwtUtil.getUsernameFromToken(jwt);
                log.debug("Username from token: {}", username);
                
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Users user = userMapper.findByUsername(username);
                    log.debug("User found: {}, role: {}", user.getUsername(), user.getRole());
                    
                    UsernamePasswordAuthenticationToken authToken = getAuthentication(jwt);
                    if (authToken != null) {
                        log.debug("Auth token created with authorities: {}", authToken.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        log.warn("Failed to create auth token");
                    }
                }
            }
        } catch (Exception e) {
            log.error("Authentication error", e);
        }
        
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        if (username != null) {
            Users user = userMapper.findByUsername(username);
            if (user != null) {
                String role = user.getRole().name();
                log.debug("User role: {}", role);
                
                return new UsernamePasswordAuthenticationToken(
                    user,
                    null, 
                    Collections.singleton(new SimpleGrantedAuthority(role))
                );
            }
        }
        return null;
    }
} 