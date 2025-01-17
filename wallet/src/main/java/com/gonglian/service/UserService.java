package com.gonglian.service;

import com.gonglian.dto.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UserService {
    Page<UserDTO> getUserList(UserQueryDTO queryDTO);
    UserDTO createUser(CreateUserDTO createUserDTO);
    UserDTO updateUser(Long userId, CreateUserDTO updateUserDTO);
    void deleteUser(Long userId);
    UserDTO getUserById(Long userId);
} 