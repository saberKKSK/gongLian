package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
@Schema(description = "店铺信息")
public class ShopDTO {
    
    @Schema(description = "平台ID")
    private Integer platform;
    
    @Schema(description = "店铺名称")
    private String shopName;
} 