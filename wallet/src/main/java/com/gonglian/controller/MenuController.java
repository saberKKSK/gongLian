package com.gonglian.controller;

import com.gonglian.dto.MenuDTO;
import com.gonglian.dto.ResponseResult;
import com.gonglian.service.MenuService;
import com.gonglian.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "菜单管理", description = "导航菜单相关接口")
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "获取用户菜单", description = "获取当前用户的导航菜单")
    @GetMapping("/user")
    public ResponseResult<List<MenuDTO>> getUserMenus() {
        log.debug("Getting menus for user: {}", SecurityUtils.getCurrentUserId());
        try {
            List<MenuDTO> menus = menuService.getUserMenus();
            log.debug("Found {} menus", menus.size());
            return ResponseResult.success(menus);
        } catch (Exception e) {
            log.error("Error getting user menus", e);
            throw e;
        }
    }
} 