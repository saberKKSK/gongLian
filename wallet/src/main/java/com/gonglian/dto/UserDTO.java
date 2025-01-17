package com.gonglian.dto;

import com.gonglian.model.Users.UserRole;
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
@Schema(description = "用户信息")
public class UserDTO {
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "支付宝账号")
    private String alipay;
    
    @Schema(description = "微信账号")
    private String wechat;
    
    @Schema(description = "用户角色")
    private UserRole role;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
} 