package com.gonglian.dto;

import com.gonglian.model.Users.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户查询条件")
public class UserQueryDTO {
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "角色")
    private UserRole role;
    
    @Schema(description = "页码")
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小")
    private Integer pageSize = 10;
} 