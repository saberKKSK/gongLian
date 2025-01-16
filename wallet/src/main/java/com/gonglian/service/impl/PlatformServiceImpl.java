package com.gonglian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gonglian.exception.BusinessException;
import com.gonglian.mapper.PlatformMapper;
import com.gonglian.model.Platforms;
import com.gonglian.service.PlatformService;
import com.gonglian.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {

    private final PlatformMapper platformMapper;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    @Override
    public List<Platforms> getPlatformList() {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        return platformMapper.selectList(new LambdaQueryWrapper<Platforms>()
                .orderByAsc(Platforms::getPlatformId));
    }

    @Override
    public Platforms addPlatform(Platforms platform) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        platform.setUserId(userId);
        platformMapper.insert(platform);
        return platform;
    }

    @Override
    public void deletePlatform(Integer platformId) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        LambdaQueryWrapper<Platforms> wrapper = new LambdaQueryWrapper<Platforms>()
                .eq(Platforms::getPlatformId, platformId)
                .eq(Platforms::getUserId, userId);

        if (platformMapper.delete(wrapper) == 0) {
            throw new BusinessException("平台不存在或无权删除");
        }
    }

    @Override
    public Platforms updatePlatform(Platforms platform) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        LambdaQueryWrapper<Platforms> wrapper = new LambdaQueryWrapper<Platforms>()
                .eq(Platforms::getPlatformId, platform.getPlatformId())
                .eq(Platforms::getUserId, userId);

        Platforms existingPlatform = platformMapper.selectOne(wrapper);
        if (existingPlatform == null) {
            throw new BusinessException("平台不存在或无权修改");
        }

        platform.setUserId(userId);
        platformMapper.updateById(platform);
        return platform;
    }
} 