package com.gonglian.dto;

import com.gonglian.model.Users.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建用户请求")
public class CreateUserDTO {
    
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;
    
    @Schema(description = "支付宝账号")
    private String alipay;
    
    @Schema(description = "微信账号")
    private String wechat;
    
    @NotNull(message = "用户角色不能为空")
    @Schema(description = "用户角色")
    private UserRole role;
} 