package com.acme.games.rps.service.impl;

import com.acme.games.rps.dao.GameDao;
import com.acme.games.rps.exception.GameAlreadyFinishedException;
import com.acme.games.rps.exception.GameNotFoundException;
import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import com.acme.games.rps.model.GameStatus;
import com.acme.games.rps.model.Move;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    GameServiceImpl gameService;

    @Mock
    GameDao gameDao;

    @Mock
    RandomChoiceServiceImpl choiceService;

    @BeforeEach
    void setUp() {
        gameService = new GameServiceImpl(gameDao, choiceService);
    }

    @Test
    void shouldCreateNewGame() {
        when(gameDao.save(any())).then(invocation -> invocation.getArgument(0));

        Game returnedGame = gameService.createGame();

        assertThat(returnedGame.getId()).isNotNull();
        assertThat(returnedGame.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(returnedGame.getMoves()).isEmpty();
        verify(gameDao).save(returnedGame);
    }

    @Test
    void shouldFindExistingGame() {
        String gameId = UUID.randomUUID().toString();
        Game existing = new Game(gameId);
        when(gameDao.findById(gameId)).thenReturn(Optional.of(existing));

        Game foundGame = gameService.getGame(gameId);

        assertThat(foundGame).isEqualTo(existing);
    }

    @Test
    void shouldFailOnMissingGame() {
        String gameId = UUID.randomUUID().toString();
        when(gameDao.findById(gameId)).thenReturn(Optional.empty());

        Throwable captured = catchThrowable(() -> gameService.getGame(gameId));

        assertThat(captured).isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void shouldMakeMoves() {
        String gameId = UUID.randomUUID().toString();
        Choice playerChoice = Choice.random();
        Choice serverChoice = Choice.random();
        ImmutableList<Move> existingMoves = ImmutableList.of(new Move(Choice.random(), Choice.random()));
        Game existingGame = new Game(gameId);
        existingMoves.forEach(existingGame::addMove);
        when(gameDao.findById(gameId)).thenReturn(Optional.of(existingGame));
        when(gameDao.save(any())).then(invocation -> invocation.getArgument(0));
        when(choiceService.makeChoice(existingGame, playerChoice)).thenReturn(serverChoice);

        Game updatedGame = gameService.makeMove(gameId, playerChoice);

        Move expectedMove = new Move(playerChoice, serverChoice);
        List<Move> expectedMoves = ImmutableList.<Move>builder().addAll(existingMoves).add(expectedMove).build();
        assertThat(updatedGame.getId()).isEqualTo(gameId);
        assertThat(updatedGame.getMoves()).containsExactlyElementsOf(expectedMoves);
        verify(gameDao).save(updatedGame);
    }

    @Test
    void shouldFailToMakeMovesOnFinishedGame() {
        String gameId = UUID.randomUUID().toString();
        Game existingGame = new Game(gameId);
        existingGame.setStatus(GameStatus.FINISHED);
        when(gameDao.findById(gameId)).thenReturn(Optional.of(existingGame));

        Throwable captured = catchThrowable(() -> gameService.makeMove(gameId, Choice.random()));

        assertThat(captured).isInstanceOf(GameAlreadyFinishedException.class);
    }

    @Test
    void shouldFinishGame() {
        String gameId = UUID.randomUUID().toString();
        Game existingGame = new Game(gameId);
        when(gameDao.findById(gameId)).thenReturn(Optional.of(existingGame));
        when(gameDao.save(any())).then(invocation -> invocation.getArgument(0));

        Game finishedGame = gameService.finishGame(gameId);

        assertThat(finishedGame.getId()).isEqualTo(gameId);
        assertThat(finishedGame.getStatus()).isEqualTo(GameStatus.FINISHED);
        verify(gameDao).save(finishedGame);
    }
}