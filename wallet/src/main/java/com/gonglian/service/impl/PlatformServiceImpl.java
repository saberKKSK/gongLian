package com.gonglian.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.gonglian.constant.RedisKeyConstant;
import com.gonglian.dto.CreatePlatformDTO;
import com.gonglian.dto.PlatformDTO;
import com.gonglian.exception.BusinessException;
import com.gonglian.mapper.PlatformMapper;
import com.gonglian.model.Platforms;
import com.gonglian.service.PlatformService;
import com.gonglian.utils.RedisUtil;
import com.gonglian.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatformServiceImpl implements PlatformService {

    private final PlatformMapper platformMapper;
    private final RedisUtil redisUtil;
    private static final CopyOptions DTO_COPY_OPTIONS = CopyOptions.create();

    @Override
    public List<PlatformDTO> getPlatformList() {
        List<Platforms> platforms = platformMapper.selectList(null);
        return platforms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlatformDTO addPlatform(CreatePlatformDTO createPlatformDTO) {
        Platforms platform = BeanUtil.toBean(createPlatformDTO, Platforms.class);
        
        // 设置当前用户ID
        platform.setUserId(SecurityUtils.getCurrentUserId());
        
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        platform.setCreatedAt(now);
        platform.setUpdatedAt(now);
        
        platformMapper.insert(platform);
        redisUtil.delete(RedisKeyConstant.PLATFORM_LIST_KEY);
        
        return convertToDTO(platform);
    }

    @Override
    public void deletePlatform(Integer platformId) {
        platformMapper.deleteById(platformId);
        redisUtil.delete(RedisKeyConstant.PLATFORM_LIST_KEY);
    }

    @Override
    public PlatformDTO updatePlatform(Integer platformId, CreatePlatformDTO updatePlatformDTO) {
        Platforms platform = platformMapper.selectById(platformId);
        if (platform == null) {
            throw new BusinessException("平台不存在");
        }
        
        BeanUtil.copyProperties(updatePlatformDTO, platform, DTO_COPY_OPTIONS);
        platform.setUpdatedAt(LocalDateTime.now());
        
        platformMapper.updateById(platform);
        redisUtil.delete(RedisKeyConstant.PLATFORM_LIST_KEY);
        
        return convertToDTO(platform);
    }

    private PlatformDTO convertToDTO(Platforms platform) {
        return BeanUtil.toBean(platform, PlatformDTO.class);
    }
} 