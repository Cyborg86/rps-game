package com.acme.games.rps.model;

import lombok.Data;

@Data
public class Move {
    private final Choice playerOneChoice;
    private final Choice playerTwoChoice;
    private final MoveResult result;
}
