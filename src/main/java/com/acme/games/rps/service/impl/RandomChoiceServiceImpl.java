package com.acme.games.rps.service.impl;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import com.acme.games.rps.service.ChoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(value = "choice.service", havingValue = "random")
@Slf4j
public class RandomChoiceServiceImpl implements ChoiceService {
    @Override
    public Choice makeChoice(Game game, Choice playerChoice) {
        Choice choice = Choice.random();
        log.debug("Server makes choice={} in game Id={}", choice, game.getId());
        return choice;
    }
}
