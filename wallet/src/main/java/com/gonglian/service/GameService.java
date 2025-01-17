package com.gonglian.service;

import com.gonglian.dto.CreateGameDTO;
import com.gonglian.dto.GameDTO;
import java.util.List;

public interface GameService {
    List<GameDTO> getGameList();
    GameDTO addGame(CreateGameDTO createGameDTO, Long userId);
    void deleteGame(Integer gameId, Long userId);
    GameDTO updateGame(Integer gameId, CreateGameDTO updateGameDTO, Long userId);
} 