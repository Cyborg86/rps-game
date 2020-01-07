package com.acme.games.rps.service;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ChoiceService {
    private final List<Choice> choices = Arrays.asList(Choice.values());

    public Choice makeChoice(Game game, Choice playerChoice) {
        return makeRandomChoice();
    }

    private Choice makeRandomChoice() {
        int choiceNumber = ThreadLocalRandom.current().nextInt(choices.size());
        return choices.get(choiceNumber);
    }
}
