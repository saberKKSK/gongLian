package com.gonglian.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gonglian.dto.*;
import com.gonglian.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public ResponseResult<Page<UserDTO>> getUserList(UserQueryDTO queryDTO) {
        return ResponseResult.success(userService.getUserList(queryDTO));
    }

    @Operation(summary = "创建用户")
    @PostMapping("/add")
    public ResponseResult<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return ResponseResult.success(userService.createUser(createUserDTO));
    }

    @Operation(summary = "更新用户")
    @PutMapping("/update/{userId}")
    public ResponseResult<UserDTO> updateUser(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Valid @RequestBody CreateUserDTO updateUserDTO) {
        return ResponseResult.success(userService.updateUser(userId, updateUserDTO));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/delete/{userId}")
    public ResponseResult<Void> deleteUser(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseResult.success(null);
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/detail/{userId}")
    public ResponseResult<UserDTO> getUserDetail(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        return ResponseResult.success(userService.getUserById(userId));
    }
} 