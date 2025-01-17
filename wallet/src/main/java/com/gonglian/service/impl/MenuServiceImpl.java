package com.gonglian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gonglian.dto.MenuDTO;
import com.gonglian.mapper.NavigationMenuMapper;
import com.gonglian.mapper.UserMapper;
import com.gonglian.model.NavigationMenu;
import com.gonglian.model.Users;
import com.gonglian.service.MenuService;
import com.gonglian.utils.SecurityUtils;
import com.gonglian.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final NavigationMenuMapper menuMapper;
    private final UserMapper userMapper;
    private final RedisUtil redisUtil;
    
    private static final String MENU_CACHE_KEY = "menu:all";
    private static final long MENU_CACHE_TTL = 24 * 60 * 60; // 24小时

    @Override
    public List<MenuDTO> getUserMenus() {
        // 获取当前用户
        Long userId = SecurityUtils.getCurrentUserId();
        Users user = userMapper.selectById(userId);
        log.debug("Getting menus for user: {}, role: {}", user.getUsername(), user.getRole());
        
        // 从Redis获取所有菜单
        List<NavigationMenu> allMenus = redisUtil.getList(MENU_CACHE_KEY, NavigationMenu.class);
        
        // 如果Redis中没有，则从数据库获取并缓存
        if (allMenus == null || allMenus.isEmpty()) {
            allMenus = menuMapper.selectList(
                new LambdaQueryWrapper<NavigationMenu>()
                    .orderByAsc(NavigationMenu::getOrderNum)
            );
            
            // 缓存到Redis
            if (!allMenus.isEmpty()) {
                redisUtil.setList(MENU_CACHE_KEY, allMenus, MENU_CACHE_TTL);
                log.debug("Cached {} menus in Redis", allMenus.size());
            }
        } else {
            log.debug("Got {} menus from Redis cache", allMenus.size());
        }

        // 根据角色过滤菜单
        List<NavigationMenu> userMenus;
        if (Users.UserRole.ADMIN.equals(user.getRole())) {
            log.debug("User is admin, returning all menus");
            userMenus = allMenus;
        } else {
            log.debug("User is not admin, filtering menus");
            Set<String> userPermissions = new HashSet<>(Arrays.asList(
                "dashboard:view",
                "shops:view",
                "orders:view",
                "games:view",
                "platforms:view"
            ));
            
            userMenus = allMenus.stream()
                .filter(menu -> {
                    String permission = menu.getPermission();
                    boolean hasPermission = permission == null || userPermissions.contains(permission);
                    log.debug("Checking menu: {}, permission: {}, hasPermission: {}", 
                        menu.getName(), permission, hasPermission);
                    return hasPermission;
                })
                .collect(Collectors.toList());
        }

        log.debug("Found {} menus for user", userMenus.size());
        return buildMenuTree(userMenus);
    }

    /**
     * 刷新菜单缓存
     */
    public void refreshMenuCache() {
        redisUtil.delete(MENU_CACHE_KEY);
        log.debug("Menu cache cleared");
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
        menuDTOs.forEach(menu -> {
            List<MenuDTO> children = parentChildMap.getOrDefault(menu.getId(), new ArrayList<>());
            // 对子菜单按 orderNum 排序
            children.sort(Comparator.comparing(MenuDTO::getOrderNum));
            menu.setChildren(children);
        });

        // 返回顶级菜单，并按 orderNum 排序
        return menuDTOs.stream()
                .filter(menu -> menu.getParentId() == null)
                .sorted(Comparator.comparing(MenuDTO::getOrderNum))
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