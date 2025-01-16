package com.gonglian.service;

import com.gonglian.model.Platforms;
import java.util.List;

public interface PlatformService {
    List<Platforms> getPlatformList();
    Platforms addPlatform(Platforms platform);
    void deletePlatform(Integer platformId);
    Platforms updatePlatform(Platforms platform);
} 