package com.gonglian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bills")
public class Bills {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 接单用户id
     */
    private Integer userId;

    /**
     * 平台
     */
    private Integer platform;

    /**
     * 游戏ID
     */
    private Integer gameId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 时长
     */
    private Float duration;

    /**
     * 段位
     */
    private String rank;

    /**
     * 是否结算 0-未结算 1-已结算
     */
    private Boolean settled;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 