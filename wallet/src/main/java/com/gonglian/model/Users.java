package com.gonglian.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("users")
public class Users {

    /**
     * 用户ID，自增主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 支付宝账号，唯一
     */
    private String alipay;

    /**
     * 微信账号
     */
    private String wechat;

    /**
     * 密码哈希值
     */
    private String passwordHash;

    /**
     * 用户角色
     */
    private UserRole role;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableLogic
    private Boolean deleted;

    /**
     * 用户角色枚举
     */
    public enum UserRole {
        /**
         * 司机
         */
        driver,
        
        /**
         * 商家
         */
        merchant,
        
        /**
         * 管理员
         */
        admin
    }
} 