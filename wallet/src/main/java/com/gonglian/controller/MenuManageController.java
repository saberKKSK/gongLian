package com.gonglian.controller;

import com.gonglian.dto.ResponseResult;
import com.gonglian.mapper.NavigationMenuMapper;
import com.gonglian.model.NavigationMenu;
import com.gonglian.service.MenuService;
import com.gonglian.service.impl.MenuServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "菜单管理", description = "菜单管理相关接口")
@RestController
@RequestMapping("/system/menus")
@RequiredArgsConstructor
public class MenuManageController {

    private final MenuService menuService;
    private final NavigationMenuMapper menuMapper;

    @Operation(summary = "添加菜单")
    @PostMapping("/add")
    public ResponseResult<Void> addMenu(@Valid @RequestBody NavigationMenu menu) {
        menuMapper.insert(menu);
        ((MenuServiceImpl) menuService).refreshMenuCache();
        return ResponseResult.success(null);
    }

    @Operation(summary = "更新菜单")
    @PutMapping("/update/{id}")
    public ResponseResult<Void> updateMenu(
            @PathVariable Long id,
            @Valid @RequestBody NavigationMenu menu) {
        menu.setId(id);
        menuMapper.updateById(menu);
        ((MenuServiceImpl) menuService).refreshMenuCache();
        return ResponseResult.success(null);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<Void> deleteMenu(@PathVariable Long id) {
        menuMapper.deleteById(id);
        ((MenuServiceImpl) menuService).refreshMenuCache();
        return ResponseResult.success(null);
    }
} 