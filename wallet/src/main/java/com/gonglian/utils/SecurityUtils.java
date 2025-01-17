package com.gonglian.utils;

import com.gonglian.exception.BusinessException;
import com.gonglian.model.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("用户未登录");
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof Users) {
            return ((Users) principal).getUserId();
        }
        
        throw new BusinessException("获取用户信息失败");
    }
} 