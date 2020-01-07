package com.acme.games.rps.service;

import com.acme.games.rps.dao.GameDao;
import com.acme.games.rps.exception.GameNotFoundException;
import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import com.acme.games.rps.model.GameStatus;
import com.acme.games.rps.model.Move;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameDao gameDao;
    private final ChoiceService choiceService;
    private final MoveService moveService;

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

        Choice ourChoice = choiceService.makeChoice(game, playerChoice);
        Move move = moveService.evaluateMove(playerChoice, ourChoice);

        game.addMove(move);
        gameDao.save(game);

        return game;
    }
}
