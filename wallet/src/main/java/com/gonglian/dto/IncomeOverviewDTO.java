package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "收入概览数据")
public class IncomeOverviewDTO {
    
    @Schema(description = "总收入金额")
    private BigDecimal totalIncome;
    
    @Schema(description = "总订单数")
    private Integer totalOrders;
    
    @Schema(description = "已结算订单数")
    private Integer settledOrders;
    
    @Schema(description = "未结算订单数")
    private Integer unsettledOrders;
} 