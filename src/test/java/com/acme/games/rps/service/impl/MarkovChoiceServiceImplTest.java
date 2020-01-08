package com.acme.games.rps.service.impl;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import com.acme.games.rps.model.Move;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static com.acme.games.rps.model.Choice.*;
import static org.assertj.core.api.Assertions.assertThat;

class MarkovChoiceServiceImplTest {
    MarkovChoiceServiceImpl choiceService = new MarkovChoiceServiceImpl();

    @Test
    void shouldGuessRepeatedChoice() {
        Game repeatedPaper = createGameWithPlayerChoices(PAPER, PAPER, PAPER, SCISSORS, ROCK, PAPER);

        Choice serverChoice = choiceService.makeChoice(repeatedPaper, Choice.random());

        assertThat(serverChoice).isEqualTo(SCISSORS);
    }

    @Test
    void shouldLearnFromFirstSequence() {
        Game rockAfterScissors = createGameWithPlayerChoices(SCISSORS, ROCK, SCISSORS);

        Choice serverChoice = choiceService.makeChoice(rockAfterScissors, Choice.random());

        assertThat(serverChoice).isEqualTo(PAPER);
    }

    @Test
    void shouldMakeRandomChoiceIfNotLearnedYet() {
        Game rockFirstTime = createGameWithPlayerChoices(PAPER, SCISSORS, ROCK);

        Choice serverChoice = choiceService.makeChoice(rockFirstTime, Choice.random());

        assertThat(serverChoice).isNotNull();
    }

    @Test
    void shouldMakeRandomChoiceForNewGame() {
        Game newGame = createGameWithPlayerChoices();

        Choice serverChoice = choiceService.makeChoice(newGame, Choice.random());

        assertThat(serverChoice).isNotNull();
    }

    private Game createGameWithPlayerChoices(Choice... choices) {
        Game game = new Game(UUID.randomUUID().toString());
        Arrays.stream(choices)
                .map(c -> new Move(c, Choice.random()))
                .forEach(game::addMove);
        return game;
    }
}