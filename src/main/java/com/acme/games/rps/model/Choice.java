package com.acme.games.rps.model;

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
