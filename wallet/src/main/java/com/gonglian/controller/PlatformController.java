package com.gonglian.controller;

import com.gonglian.dto.CreatePlatformDTO;
import com.gonglian.dto.PlatformDTO;
import com.gonglian.dto.ResponseResult;
import com.gonglian.service.PlatformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "平台管理", description = "平台相关接口")
@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;

    @Operation(summary = "平台列表", description = "获取所有平台信息")
    @GetMapping("/list")
    public ResponseResult<List<PlatformDTO>> getPlatformList() {
        return ResponseResult.success(platformService.getPlatformList());
    }

    @Operation(summary = "添加平台", description = "创建新的平台")
    @PostMapping("/add")
    public ResponseResult<PlatformDTO> addPlatform(
            @Valid @RequestBody CreatePlatformDTO createPlatformDTO) {
        return ResponseResult.success(platformService.addPlatform(createPlatformDTO));
    }

    @Operation(summary = "删除平台", description = "删除指定的平台")
    @DeleteMapping("/delete/{platformId}")
    public ResponseResult<Void> deletePlatform(
            @Parameter(description = "平台ID") @PathVariable Integer platformId) {
        platformService.deletePlatform(platformId);
        return ResponseResult.success(null);
    }

    @Operation(summary = "更新平台", description = "更新平台信息")
    @PutMapping("/update/{platformId}")
    public ResponseResult<PlatformDTO> updatePlatform(
            @Parameter(description = "平台ID") @PathVariable Integer platformId,
            @Valid @RequestBody CreatePlatformDTO updatePlatformDTO) {
        return ResponseResult.success(platformService.updatePlatform(platformId, updatePlatformDTO));
    }
} 