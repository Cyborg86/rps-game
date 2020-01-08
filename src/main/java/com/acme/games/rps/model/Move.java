package com.acme.games.rps.model;

import lombok.Data;

@Data
public class Move {
    private final Choice playerChoice;
    private final Choice serverChoice;

    public MoveResult getResult() {
        return playerChoice.evaluateVs(serverChoice);
    }
}
