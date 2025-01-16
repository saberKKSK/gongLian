package com.gonglian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gonglian.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<Users> {
    
    @Select("SELECT * FROM users WHERE username = #{username} AND deleted = 0")
    Users findByUsername(String username);
} 