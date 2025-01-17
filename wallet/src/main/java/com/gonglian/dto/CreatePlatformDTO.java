package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "创建平台请求")
public class CreatePlatformDTO {
    
    @Schema(description = "平台名称")
    @NotBlank(message = "平台名称不能为空")
    private String name;
    
    @Schema(description = "平台描述")
    private String description;
} 