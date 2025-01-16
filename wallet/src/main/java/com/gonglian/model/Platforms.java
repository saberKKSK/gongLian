package com.gonglian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 平台信息实体类
 * 存储用户自定义平台信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("platforms")
public class Platforms {

    /**
     * 平台的唯一标识符，自增主键
     */
    @TableId(value = "platform_id", type = IdType.AUTO)
    private Integer platformId;

    /**
     * 用户ID，关联到 users 表的 user_id
     */
    private Long userId;

    /**
     * 平台名称（如 "淘宝"、"京东"）
     */
    private String platformName;

    /**
     * 平台代码（如 "TB" 代表淘宝）
     */
    private String platformCode;

    /**
     * 平台描述信息，可选
     */
    private String description;

    /**
     * 记录创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 记录更新时间
     */
    private LocalDateTime updatedAt;
} 