package com.acme.games.rps.controller;

import com.acme.games.rps.exception.GameAlreadyFinishedException;
import com.acme.games.rps.exception.GameNotFoundException;
import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import com.acme.games.rps.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    GameService service;

    @Test
    public void newGameCouldBeCreated() throws Exception {
        Game newGame = new Game(UUID.randomUUID().toString());
        when(service.createGame()).thenReturn(newGame);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/games")).andDo(print());

        verify(service).createGame();
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(newGame.getId()));
    }

    @Test
    public void movesCouldBeMadeInGame() throws Exception {
        String gameId = UUID.randomUUID().toString();
        Choice userChoice = Choice.SCISSORS;
        Game existingGame = new Game(gameId);
        when(service.makeMove(gameId, userChoice)).thenReturn(existingGame);

        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/games/{id}/moves", gameId).param("playerChoice", userChoice.toString()))
                .andDo(print());

        verify(service).makeMove(gameId, userChoice);
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(gameId));
    }

    @Test
    public void gameCouldBeFoundById() throws Exception {
        String gameId = UUID.randomUUID().toString();
        Game existingGame = new Game(gameId);
        when(service.getGame(gameId)).thenReturn(existingGame);

        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/games/{id}", gameId))
                .andDo(print());

        verify(service).getGame(gameId);
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(gameId));
    }

    @Test
    public void gameCouldBeFinished() throws Exception {
        String gameId = UUID.randomUUID().toString();
        Game existingGame = new Game(gameId);
        when(service.finishGame(gameId)).thenReturn(existingGame);

        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/games/{id}/finish", gameId))
                .andDo(print());

        verify(service).finishGame(gameId);
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(gameId));
    }

    @Test
    public void gameNotFoundIsMappedTo404() throws Exception {
        String gameId = UUID.randomUUID().toString();
        when(service.getGame(gameId)).thenThrow(new GameNotFoundException(gameId));

        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/games/{id}", gameId))
                .andDo(print());

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(stringContainsInOrder(gameId)));
    }

    @Test
    public void alreadyFinishedGameIsMappedTo400() throws Exception {
        String gameId = UUID.randomUUID().toString();
        Choice userChoice = Choice.ROCK;
        when(service.makeMove(gameId, userChoice)).thenThrow(new GameAlreadyFinishedException(gameId));

        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/games/{id}/moves", gameId).param("playerChoice", userChoice.toString()))
                .andDo(print());

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(stringContainsInOrder(gameId)));
    }

    @Test
    public void generalErrorIsMappedTo500() throws Exception {
        String gameId = UUID.randomUUID().toString();
        String testMessage = "Test message";
        when(service.getGame(gameId)).thenThrow(new RuntimeException(testMessage));

        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/games/{id}", gameId))
                .andDo(print());

        resultActions
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(testMessage));
    }
}