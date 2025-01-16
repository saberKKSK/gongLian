package com.gonglian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 游戏信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("games")
public class Games {

    /**
     * 游戏的唯一标识符，自增主键
     */
    @TableId(value = "game_id", type = IdType.AUTO)
    private Integer gameId;

    /**
     * 用户ID，关联到 users 表的 user_id
     */
    private Long userId;

    /**
     * 游戏名称（如 "APEX"、"LOL"、"王者荣耀"）
     */
    private String gameName;

    /**
     * 游戏代码（如 "APEX" 代表 APEX Legends）
     */
    private String gameCode;

    /**
     * 游戏描述信息，可选
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