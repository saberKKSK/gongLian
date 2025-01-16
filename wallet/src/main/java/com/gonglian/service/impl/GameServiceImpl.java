package com.gonglian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gonglian.exception.BusinessException;
import com.gonglian.mapper.GameMapper;
import com.gonglian.model.Games;
import com.gonglian.service.GameService;
import com.gonglian.utils.RedisUtil;
import com.gonglian.constant.RedisKeyConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameMapper gameMapper;
    private final RedisUtil redisUtil;

    @Override
    public List<Games> getGameList() {
        // 先从Redis获取
        List<Games> games = redisUtil.getList(RedisKeyConstant.GAME_LIST_KEY, Games.class);
        if (games != null && !games.isEmpty()) {
            return games;
        }

        // Redis没有，从数据库获取
        games = gameMapper.selectList(new LambdaQueryWrapper<Games>()
                .orderByAsc(Games::getGameId));
        
        // 存入Redis
        if (!games.isEmpty()) {
            redisUtil.setList(RedisKeyConstant.GAME_LIST_KEY, games, RedisKeyConstant.CACHE_EXPIRE_TIME);
        }
        
        return games;
    }

    @Override
    public Games addGame(Games game) {
        gameMapper.insert(game);
        // 删除缓存
        redisUtil.delete(RedisKeyConstant.GAME_LIST_KEY);
        return game;
    }

    @Override
    public void deleteGame(Integer gameId) {
        if (gameMapper.deleteById(gameId) == 0) {
            throw new BusinessException("游戏不存在");
        }
        // 删除缓存
        redisUtil.delete(RedisKeyConstant.GAME_LIST_KEY);
    }

    @Override
    public Games updateGame(Games game) {
        Games existingGame = gameMapper.selectById(game.getGameId());
        if (existingGame == null) {
            throw new BusinessException("游戏不存在");
        }
        
        gameMapper.updateById(game);
        // 删除缓存
        redisUtil.delete(RedisKeyConstant.GAME_LIST_KEY);
        return game;
    }
} 