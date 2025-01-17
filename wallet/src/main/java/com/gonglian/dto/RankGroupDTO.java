package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "段位分组统计")
public class RankGroupDTO {
    
    @Schema(description = "项目")
    private String rank;

    @Schema(description = "总金额")
    private BigDecimal totalAmount;
    
    @Schema(description = "总时长(小时)")
    private Double totalDuration;

} 