package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "导航菜单")
public class MenuDTO {
    
    @Schema(description = "菜单ID")
    private Long id;
    
    @Schema(description = "父级菜单ID")
    private Long parentId;
    
    @Schema(description = "菜单名称")
    private String name;
    
    @Schema(description = "路由路径")
    private String path;
    
    @Schema(description = "图标")
    private String icon;
    
    @Schema(description = "排序号")
    private Integer orderNum;
    
    @Schema(description = "权限标识")
    private String permission;
    
    @Schema(description = "子菜单")
    private List<MenuDTO> children;
} 