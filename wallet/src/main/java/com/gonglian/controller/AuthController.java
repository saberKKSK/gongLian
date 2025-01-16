package com.gonglian.controller;

import com.gonglian.dto.LoginDTO;
import com.gonglian.dto.ResponseResult;
import com.gonglian.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证管理", description = "用户认证相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户登录", description = "用户名密码登录获取token")
    @PostMapping("/login")
    public ResponseResult<String> login(
            @Parameter(description = "登录信息", required = true)
            @Valid @RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        return ResponseResult.success(token);
    }
} 