package com.gonglian.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户角色关联实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_roles")
public class UserRoles {

    /**
     * 用户ID，联合主键之一
     * 关联到 users 表的 user_id
     */
    private Long userId;

    /**
     * 角色ID，联合主键之一
     * 关联到 roles 表的 id
     */
    private Integer roleId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 