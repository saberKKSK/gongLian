package com.gonglian.controller;

import com.gonglian.dto.CreateGameDTO;
import com.gonglian.dto.GameDTO;
import com.gonglian.dto.ResponseResult;
import com.gonglian.service.GameService;
import com.gonglian.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "游戏管理", description = "游戏相关接口")
@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @Operation(summary = "游戏列表", description = "获取所有游戏信息")
    @GetMapping("/list")
    public ResponseResult<List<GameDTO>> getGameList() {
        return ResponseResult.success(gameService.getGameList());
    }

    @Operation(summary = "添加游戏", description = "创建新的游戏")
    @PostMapping("/add")
    public ResponseResult<GameDTO> addGame(@Valid @RequestBody CreateGameDTO createGameDTO) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseResult.success(gameService.addGame(createGameDTO, userId));
    }

    @Operation(summary = "删除游戏", description = "删除指定的游戏")
    @DeleteMapping("/delete/{gameId}")
    public ResponseResult<Void> deleteGame(
            @Parameter(description = "游戏ID") @PathVariable Integer gameId) {
        Long userId = SecurityUtils.getCurrentUserId();
        gameService.deleteGame(gameId, userId);
        return ResponseResult.success(null);
    }

    @Operation(summary = "更新游戏", description = "更新游戏信息")
    @PutMapping("/update/{gameId}")
    public ResponseResult<GameDTO> updateGame(
            @Parameter(description = "游戏ID") @PathVariable Integer gameId,
            @Valid @RequestBody CreateGameDTO updateGameDTO) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseResult.success(gameService.updateGame(gameId, updateGameDTO, userId));
    }
} 