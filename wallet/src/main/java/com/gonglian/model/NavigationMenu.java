package com.gonglian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 导航菜单实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("navigation_menu")
public class NavigationMenu {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级菜单ID，顶级菜单为NULL
     */
    private Long parentId;

    /**
     * 导航项名称
     */
    private String name;

    /**
     * 导航项的路由路径
     */
    private String path;

    /**
     * 导航项的图标
     */
    private String icon;

    /**
     * 排序字段，用于控制显示顺序
     */
    private Integer orderNum;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 