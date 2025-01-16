package com.gonglian.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("bills")
public class Bills {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;
    
    private Integer platform;
    
    @TableField("game_id")
    private Integer gameId;
    
    private BigDecimal price;
    
    private Double duration;
    
    private String ranked;
    
    /**
     * 订单状态：0-未结算，1-已结算
     */
    private Boolean settled;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
} 