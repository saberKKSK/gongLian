package com.gonglian.controller;

import com.gonglian.dto.ResponseResult;
import com.gonglian.model.Games;
import com.gonglian.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "游戏管理", description = "游戏相关接口")
@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @Operation(summary = "游戏列表", description = "获取所有游戏")
    @GetMapping("/list")
    public ResponseResult<List<Games>> getGameList() {
        return ResponseResult.success(gameService.getGameList());
    }

    @Operation(summary = "添加游戏", description = "添加新的游戏")
    @PostMapping("/add")
    public ResponseResult<Games> addGame(@RequestBody Games game) {
        return ResponseResult.success(gameService.addGame(game));
    }

    @Operation(summary = "删除游戏", description = "删除指定的游戏")
    @DeleteMapping("/delete/{gameId}")
    public ResponseResult<Void> deleteGame(
            @Parameter(description = "游戏ID") @PathVariable Integer gameId) {
        gameService.deleteGame(gameId);
        return ResponseResult.success(null);
    }

    @Operation(summary = "更新游戏", description = "更新游戏信息")
    @PutMapping("/update")
    public ResponseResult<Games> updateGame(@RequestBody Games game) {
        return ResponseResult.success(gameService.updateGame(game));
    }
} 