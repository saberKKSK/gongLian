package com.gonglian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gonglian.exception.BusinessException;
import com.gonglian.mapper.PlatformMapper;
import com.gonglian.model.Platforms;
import com.gonglian.service.PlatformService;
import com.gonglian.utils.JwtUtil;
import com.gonglian.utils.RedisUtil;
import com.gonglian.constant.RedisKeyConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {

    private final PlatformMapper platformMapper;
    private final RedisUtil redisUtil;

    @Override
    public List<Platforms> getPlatformList() {
        // 先从Redis获取
        List<Platforms> platforms = redisUtil.getList(RedisKeyConstant.PLATFORM_LIST_KEY, Platforms.class);
        if (platforms != null && !platforms.isEmpty()) {
            return platforms;
        }

        // Redis没有，从数据库获取
        platforms = platformMapper.selectList(new LambdaQueryWrapper<Platforms>()
                .orderByAsc(Platforms::getPlatformId));
        
        // 存入Redis
        if (!platforms.isEmpty()) {
            redisUtil.setList(RedisKeyConstant.PLATFORM_LIST_KEY, platforms, RedisKeyConstant.CACHE_EXPIRE_TIME);
        }
        
        return platforms;
    }

    @Override
    public Platforms addPlatform(Platforms platform) {
        platformMapper.insert(platform);
        // 删除缓存，下次查询时重新加载
        redisUtil.delete(RedisKeyConstant.PLATFORM_LIST_KEY);
        return platform;
    }

    @Override
    public void deletePlatform(Integer platformId) {
        if (platformMapper.deleteById(platformId) == 0) {
            throw new BusinessException("平台不存在");
        }
        // 删除缓存
        redisUtil.delete(RedisKeyConstant.PLATFORM_LIST_KEY);
    }

    @Override
    public Platforms updatePlatform(Platforms platform) {
        Platforms existingPlatform = platformMapper.selectById(platform.getPlatformId());
        if (existingPlatform == null) {
            throw new BusinessException("平台不存在");
        }
        
        platformMapper.updateById(platform);
        // 删除缓存
        redisUtil.delete(RedisKeyConstant.PLATFORM_LIST_KEY);
        return platform;
    }
} 