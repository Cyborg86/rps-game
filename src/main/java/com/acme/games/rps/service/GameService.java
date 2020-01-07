package com.acme.games.rps.service;

import com.acme.games.rps.dao.GameDao;
import com.acme.games.rps.exception.GameNotFoundException;
import com.acme.games.rps.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameDao gameDao;
    private final ChoiceService choiceService;

    public Game createGame() {
        String id = UUID.randomUUID().toString();
        Game game = new Game(id);

        return gameDao.save(game);
    }

    public Game finishGame(String id) {
        Game game = getGame(id);
        game.setStatus(GameStatus.FINISHED);
        return gameDao.save(game);
    }

    public Game getGame(String id) {
        return gameDao.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    public Game makeMove(String gameId, Choice playerChoice) {
        Game game = getGame(gameId);
        //TODO: add finalized game check

        Choice serverChoice = choiceService.makeChoice(game, playerChoice);
        MoveResult result = playerChoice.evaluateVs(serverChoice);
        Move move = new Move(playerChoice, serverChoice, result);

        game.addMove(move);
        gameDao.save(game);

        return game;
    }
}
