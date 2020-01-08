package com.acme.games.rps.service;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;

public interface GameService {
    Game createGame();

    Game finishGame(String id);

    Game getGame(String id);

    Game makeMove(String gameId, Choice playerChoice);
}
