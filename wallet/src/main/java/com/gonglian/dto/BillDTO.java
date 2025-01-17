package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "账单信息")
public class BillDTO {
    
    @Schema(description = "账单ID")
    private Long id;
    
    @Schema(description = "平台：0-默认平台")
    private Integer platform;
    
    @Schema(description = "游戏ID")
    private Integer gameId;
    
    @Schema(description = "金额")
    private BigDecimal price;
    
    @Schema(description = "工作时长(小时)")
    private Double duration;
    
    @Schema(description = "段位")
    private String rank;
    
    @Schema(description = "是否已结算")
    private Boolean settled;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
} 