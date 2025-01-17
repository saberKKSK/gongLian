package com.gonglian.service;

import com.gonglian.dto.CreatePlatformDTO;
import com.gonglian.dto.PlatformDTO;
import java.util.List;

public interface PlatformService {
    List<PlatformDTO> getPlatformList();
    PlatformDTO addPlatform(CreatePlatformDTO createPlatformDTO);
    void deletePlatform(Integer platformId);
    PlatformDTO updatePlatform(Integer platformId, CreatePlatformDTO updatePlatformDTO);
} 