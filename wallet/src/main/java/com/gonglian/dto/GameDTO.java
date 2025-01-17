package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "游戏信息")
public class GameDTO {
    
    @Schema(description = "游戏ID")
    private Integer gameId;
    
    @Schema(description = "游戏名称")
    private String gameName;
    
    @Schema(description = "游戏描述")
    private String description;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
} 