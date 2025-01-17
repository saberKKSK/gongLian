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
     * 游戏ID
     */
    @TableId(value = "game_id", type = IdType.AUTO)
    private Integer gameId;


    /**
     * 游戏ID
     */
    private Long userId;


    /**
     * 游戏名称
     */
    private String gameName;

    /**
     * 游戏代码
     */
    private String gameCode;

    /**
     * 游戏描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 