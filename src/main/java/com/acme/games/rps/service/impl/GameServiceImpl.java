package com.acme.games.rps.service.impl;

import com.acme.games.rps.dao.GameDao;
import com.acme.games.rps.exception.GameAlreadyFinishedException;
import com.acme.games.rps.exception.GameNotFoundException;
import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import com.acme.games.rps.model.GameStatus;
import com.acme.games.rps.model.Move;
import com.acme.games.rps.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements com.acme.games.rps.service.GameService {
    private final GameDao gameDao;
    private final ChoiceService choiceService;

    @Override
    public Game createGame() {
        log.debug("Creating a new game...");
        String id = UUID.randomUUID().toString();
        Game game = new Game(id);

        Game saved = gameDao.save(game);

        log.debug("Game Id={} created", id);
        return saved;
    }

    @Override
    public Game finishGame(String id) {
        log.debug("Finishing game Id={}", id);
        Game game = getGame(id);
        game.setStatus(GameStatus.FINISHED);
        Game saved = gameDao.save(game);

        log.debug("Game Id={} finished", id);
        return saved;
    }

    @Override
    public Game getGame(String id) {
        log.debug("Searching for game Id={}", id);
        return gameDao.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public Game makeMove(String gameId, Choice playerChoice) {
        log.debug("Player made a move in game Id={}. Player choice is {}", gameId, playerChoice);

        Game game = getGame(gameId);
        if (game.getStatus() == GameStatus.FINISHED) {
            throw new GameAlreadyFinishedException(gameId);
        }

        Choice serverChoice = choiceService.makeChoice(game, playerChoice);
        Move move = new Move(playerChoice, serverChoice);

        game.addMove(move);
        Game saved = gameDao.save(game);
        log.debug("A new move in game Id={}:  {}", gameId, move);
        return saved;
    }
}
