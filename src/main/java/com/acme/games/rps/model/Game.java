package com.acme.games.rps.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {
    @Id
    private final String id;
    private List<Move> moves = new ArrayList<>();
    private GameStatus status = GameStatus.IN_PROGRESS;

    public void addMove(Move move) {
        moves.add(move);
    }
}
