package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "创建订单请求")
public class CreateBillDTO {
    
    @Schema(description = "平台：0-默认平台")
    @NotNull(message = "平台不能为空")
    private Integer platform;
    
    @Schema(description = "游戏ID")
    @NotNull(message = "游戏ID不能为空")
    private Integer gameId;
    
    @Schema(description = "金额")
    @NotNull(message = "金额不能为空")
    @Min(value = 0, message = "金额必须大于0")
    private BigDecimal price;
    
    @Schema(description = "工作时长(小时)")
    @NotNull(message = "工作时长不能为空")
    @Min(value = 0, message = "工作时长必须大于0")
    private Double duration;
    
    @Schema(description = "段位")
    private String rank;
} 