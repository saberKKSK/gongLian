package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "创建游戏请求")
public class CreateGameDTO {
    
    @Schema(description = "游戏名称")
    @NotBlank(message = "游戏名称不能为空")
    private String gameName;
    
    @Schema(description = "游戏描述")
    private String description;
} 