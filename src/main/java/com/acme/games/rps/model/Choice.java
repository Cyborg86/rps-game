package com.acme.games.rps.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public enum Choice {
    ROCK {
        @Override
        public Choice beatedBy() {
            return PAPER;
        }
    },

    PAPER {
        @Override
        public Choice beatedBy() {
            return SCISSORS;
        }
    },

    SCISSORS {
        @Override
        public Choice beatedBy() {
            return ROCK;
        }
    };

    private static final List<Choice> CHOICES = Arrays.asList(Choice.values());

    public MoveResult evaluateVs(Choice other) {
        Objects.requireNonNull(other);
        if (other == this) {
            return MoveResult.DRAW;
        } else if (other == this.beatedBy()) {
            return MoveResult.LOOSE;
        } else {
            return MoveResult.WIN;
        }
    }

    public static Choice random() {
        int choiceNumber = ThreadLocalRandom.current().nextInt(CHOICES.size());
        return CHOICES.get(choiceNumber);
    }

    public abstract Choice beatedBy();
}
