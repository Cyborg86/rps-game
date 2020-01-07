package com.acme.games.rps.service;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class ChoiceService {
    private final List<Choice> choices = Arrays.asList(Choice.values());

    public Choice makeChoice(Game game, Choice playerChoice) {
        Choice choice = makeRandomChoice();
        log.debug("Server makes choice={} in game Id={}", choice, game.getId());
        return choice;
    }

    private Choice makeRandomChoice() {
        int choiceNumber = ThreadLocalRandom.current().nextInt(choices.size());
        return choices.get(choiceNumber);
    }
}
