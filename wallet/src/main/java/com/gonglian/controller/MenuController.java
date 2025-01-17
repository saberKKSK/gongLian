package com.gonglian.controller;

import com.gonglian.dto.MenuDTO;
import com.gonglian.dto.ResponseResult;
import com.gonglian.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "菜单管理", description = "导航菜单相关接口")
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "获取用户菜单", description = "获取当前用户的导航菜单")
    @GetMapping("/user")
    public ResponseResult<List<MenuDTO>> getUserMenus() {
        return ResponseResult.success(menuService.getUserMenus());
    }
} 