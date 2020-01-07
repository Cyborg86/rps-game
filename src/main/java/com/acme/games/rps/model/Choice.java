package com.acme.games.rps.model;

import java.util.Objects;

public enum Choice {
    ROCK {
        @Override
        public Choice beats() {
            return SCISSORS;
        }
    },

    PAPER {
        @Override
        public Choice beats() {
            return ROCK;
        }
    },

    SCISSORS {
        @Override
        public Choice beats() {
            return PAPER;
        }
    };

    public MoveResult evaluateVs(Choice other) {
        Objects.requireNonNull(other);
        if (other == this) {
            return MoveResult.DRAW;
        } else if (other == this.beats()) {
            return MoveResult.WIN;
        } else {
            return MoveResult.LOOSE;
        }
    }

    public abstract Choice beats();
}
