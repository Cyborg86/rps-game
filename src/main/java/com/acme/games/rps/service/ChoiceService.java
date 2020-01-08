package com.acme.games.rps.service;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;

public interface ChoiceService {

    /**
     * We are passing current player choice for more flexibility in possible algorithms.
     * <p>
     * At some point we might want our implementations to play unfair in both ways: to cheat and to succumb to player.
     * For example: most of human players might get upset if they loose 5 times in a row, but it is a quite probable event.
     * So we could implement a logic to prevent too long loose streaks.
     */
    Choice makeChoice(Game game, Choice playerChoice);
}
