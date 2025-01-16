package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "未结算订单数据")
public class UnsettledOrdersDTO {
    
    @Schema(description = "待结算总金额")
    private BigDecimal totalPendingAmount;
    
    @Schema(description = "未结算订单列表")
    private List<OrderDetailDTO> orders;
} 