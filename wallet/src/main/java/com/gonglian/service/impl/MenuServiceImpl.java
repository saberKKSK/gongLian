package com.gonglian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gonglian.dto.MenuDTO;
import com.gonglian.mapper.NavigationMenuMapper;
import com.gonglian.model.NavigationMenu;
import com.gonglian.service.MenuService;
import com.gonglian.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final NavigationMenuMapper menuMapper;
    private final JwtUtil jwtUtil;

    @Override
    public List<MenuDTO> getUserMenus() {
        // 获取所有菜单
        List<NavigationMenu> allMenus = menuMapper.selectList(
            new LambdaQueryWrapper<NavigationMenu>()
                .orderByAsc(NavigationMenu::getOrderNum)
        );

        // 转换为DTO并构建树形结构
        return buildMenuTree(allMenus);
    }

    private List<MenuDTO> buildMenuTree(List<NavigationMenu> menus) {
        // 转换为DTO
        List<MenuDTO> menuDTOs = menus.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // 构建父子关系
        Map<Long, List<MenuDTO>> parentChildMap = menuDTOs.stream()
                .filter(menu -> menu.getParentId() != null)
                .collect(Collectors.groupingBy(MenuDTO::getParentId));

        // 设置子菜单
        menuDTOs.forEach(menu -> 
            menu.setChildren(parentChildMap.getOrDefault(menu.getId(), new ArrayList<>()))
        );

        // 返回顶级菜单
        return menuDTOs.stream()
                .filter(menu -> menu.getParentId() == null)
                .collect(Collectors.toList());
    }

    private MenuDTO convertToDTO(NavigationMenu menu) {
        return MenuDTO.builder()
                .id(menu.getId())
                .parentId(menu.getParentId())
                .name(menu.getName())
                .path(menu.getPath())
                .icon(menu.getIcon())
                .orderNum(menu.getOrderNum())
                .permission(menu.getPermission())
                .children(new ArrayList<>())
                .build();
    }
} 