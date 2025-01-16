package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "订单查询条件")
public class OrderQueryDTO {
    
    @Schema(description = "段位")
    private String rank;
    
    @Schema(description = "游戏ID")
    private Integer gameId;
    
    @Schema(description = "平台")
    private Integer platform;
    
    @Schema(description = "是否已结算")
    private Boolean settled;
    
    @Schema(description = "页码")
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小")
    private Integer pageSize = 10;
} 