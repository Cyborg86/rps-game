package com.acme.games.rps.service;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ChoiceServiceTest {
    ChoiceService choiceService = new ChoiceService();

    @Test
    void shouldMakeChoice() {
        Game game = mock(Game.class);
        Choice clientChoice = mock(Choice.class);

        Choice serverChoice = choiceService.makeChoice(game, clientChoice);

        assertThat(serverChoice).isNotNull();
    }
}