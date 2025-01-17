package com.gonglian.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gonglian.dto.*;
import com.gonglian.exception.BusinessException;
import com.gonglian.mapper.UserMapper;
import com.gonglian.model.Users;
import com.gonglian.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDTO> getUserList(UserQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(queryDTO.getUsername())) {
            wrapper.like(Users::getUsername, queryDTO.getUsername());
        }
        if (queryDTO.getRole() != null) {
            wrapper.eq(Users::getRole, queryDTO.getRole());
        }
        
        // 执行分页查询
        Page<Users> page = userMapper.selectPage(
            new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
            wrapper
        );
        
        // 转换为DTO
        Page<UserDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        dtoPage.setRecords(page.getRecords().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList()));
            
        return dtoPage;
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        // 检查用户名是否已存在
        if (userMapper.selectCount(new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, createUserDTO.getUsername())) > 0) {
            throw new BusinessException("用户名已存在");
        }
        
        Users user = new Users();
        BeanUtil.copyProperties(createUserDTO, user);
        
        // 加密密码
        user.setPasswordHash(passwordEncoder.encode(createUserDTO.getPassword()));
        
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        
        userMapper.insert(user);
        return convertToDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, CreateUserDTO updateUserDTO) {
        Users user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 检查用户名是否已被其他用户使用
        if (!user.getUsername().equals(updateUserDTO.getUsername()) &&
            userMapper.selectCount(new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, updateUserDTO.getUsername())) > 0) {
            throw new BusinessException("用户名已存在");
        }
        
        BeanUtil.copyProperties(updateUserDTO, user);
        
        // 如果提供了新密码，则更新密码
        if (StringUtils.hasText(updateUserDTO.getPassword())) {
            user.setPasswordHash(passwordEncoder.encode(updateUserDTO.getPassword()));
        }
        
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateById(user);
        
        return convertToDTO(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userMapper.deleteById(userId) == 0) {
            throw new BusinessException("用户不存在");
        }
    }

    @Override
    public UserDTO getUserById(Long userId) {
        Users user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return convertToDTO(user);
    }

    private UserDTO convertToDTO(Users user) {
        return BeanUtil.toBean(user, UserDTO.class);
    }
} 