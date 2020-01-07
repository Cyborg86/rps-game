package com.acme.games.rps.model;

import lombok.Data;

@Data
public class Move {
    private final Choice playerChoice;
    private final Choice serverChoice;
    private final MoveResult result;
}
