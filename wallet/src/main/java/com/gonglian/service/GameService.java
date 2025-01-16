package com.gonglian.service;

import com.gonglian.model.Games;
import java.util.List;

public interface GameService {
    List<Games> getGameList();
    Games addGame(Games game);
    void deleteGame(Integer gameId);
    Games updateGame(Games game);
} 