package com.acme.games.rps.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.acme.games.rps.model.Choice.*;
import static com.acme.games.rps.model.MoveResult.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ChoiceTest {
    static Stream<Arguments> rules() {
        return Stream.of(
                arguments(ROCK, WIN, SCISSORS),
                arguments(ROCK, DRAW, ROCK),
                arguments(ROCK, LOOSE, PAPER),

                arguments(PAPER, WIN, ROCK),
                arguments(PAPER, DRAW, PAPER),
                arguments(PAPER, LOOSE, SCISSORS),

                arguments(SCISSORS, WIN, PAPER),
                arguments(SCISSORS, DRAW, SCISSORS),
                arguments(SCISSORS, LOOSE, ROCK)
        );
    }

    @ParameterizedTest
    @MethodSource("rules")
    public void evaluation(Choice choice, MoveResult result, Choice otherChoice) {
        assertThat(choice.evaluateVs(otherChoice)).as("%s vs %s", choice, otherChoice).isEqualTo(result);
    }
}