package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "店铺统计数据")
public class ShopStatsDTO {
    
    @Schema(description = "平台")
    private Integer platform;
    
    @Schema(description = "订单数量")
    private Long orderCount;
    
    @Schema(description = "总金额")
    private BigDecimal totalAmount;
    
    @Schema(description = "总时长")
    private Double totalDuration;
} 