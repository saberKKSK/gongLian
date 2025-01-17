package com.gonglian.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gonglian.constant.RedisKeyConstant;
import com.gonglian.dto.CreateGameDTO;
import com.gonglian.dto.GameDTO;
import com.gonglian.exception.BusinessException;
import com.gonglian.mapper.GameMapper;
import com.gonglian.model.Games;
import com.gonglian.service.GameService;
import com.gonglian.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameMapper gameMapper;
    private final RedisUtil redisUtil;
    private static final CopyOptions DTO_COPY_OPTIONS = CopyOptions.create();

    @Override
    public List<GameDTO> getGameList() {
        // 先从Redis获取
        List<Games> games = redisUtil.getList(RedisKeyConstant.GAME_LIST_KEY, Games.class);
        if (games != null && !games.isEmpty()) {
            return games.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        // Redis没有，从数据库获取
        games = gameMapper.selectList(new LambdaQueryWrapper<Games>()
                .orderByAsc(Games::getGameId));
        
        // 存入Redis
        if (!games.isEmpty()) {
            redisUtil.setList(RedisKeyConstant.GAME_LIST_KEY, games, RedisKeyConstant.CACHE_EXPIRE_TIME);
        }
        
        return games.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GameDTO addGame(CreateGameDTO createGameDTO, Long userId) {
        // 检查游戏名称是否已存在
        if (gameMapper.selectCount(new LambdaQueryWrapper<Games>()
                .eq(Games::getGameName, createGameDTO.getGameName())) > 0) {
            throw new BusinessException("游戏名称已存在");
        }

        Games game = BeanUtil.toBean(createGameDTO, Games.class);
        LocalDateTime now = LocalDateTime.now();
        game.setCreatedAt(now);
        game.setUpdatedAt(now);
        game.setUserId(userId);
        
        gameMapper.insert(game);
        redisUtil.delete(RedisKeyConstant.GAME_LIST_KEY);
        log.info("用户 {} 添加了新游戏: {}", userId, createGameDTO.getGameName());
        
        return convertToDTO(game);
    }

    @Override
    public void deleteGame(Integer gameId, Long userId) {
        Games game = gameMapper.selectOne(new LambdaQueryWrapper<Games>()
                .eq(Games::getGameId, gameId)
                .eq(Games::getUserId, userId));

        if (game == null) {
            throw new BusinessException("游戏不存在或无权删除");
        }

        gameMapper.deleteById(gameId);
        redisUtil.delete(RedisKeyConstant.GAME_LIST_KEY);
        log.info("用户 {} 删除了游戏: {}", userId, game.getGameName());
    }

    @Override
    public GameDTO updateGame(Integer gameId, CreateGameDTO updateGameDTO, Long userId) {
        Games game = gameMapper.selectOne(new LambdaQueryWrapper<Games>()
                .eq(Games::getGameId, gameId)
                .eq(Games::getUserId, userId));

        if (game == null) {
            throw new BusinessException("游戏不存在或无权修改");
        }

        if (!game.getGameName().equals(updateGameDTO.getGameName()) &&
            gameMapper.selectCount(new LambdaQueryWrapper<Games>()
                    .eq(Games::getGameName, updateGameDTO.getGameName())) > 0) {
            throw new BusinessException("游戏名称已存在");
        }
        
        BeanUtil.copyProperties(updateGameDTO, game, DTO_COPY_OPTIONS);
        game.setUpdatedAt(LocalDateTime.now());
        
        gameMapper.updateById(game);
        redisUtil.delete(RedisKeyConstant.GAME_LIST_KEY);
        log.info("用户 {} 更新了游戏: {}", userId, game.getGameName());
        
        return convertToDTO(game);
    }

    private GameDTO convertToDTO(Games game) {
        return BeanUtil.toBean(game, GameDTO.class);
    }
} 