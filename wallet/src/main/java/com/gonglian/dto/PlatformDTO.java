package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "平台信息")
public class PlatformDTO {
    
    @Schema(description = "平台ID")
    private Integer platformId;
    
    @Schema(description = "平台名称")
    private String platformName;
    
    @Schema(description = "平台描述")
    private String description;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
} 