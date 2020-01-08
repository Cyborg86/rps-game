package com.acme.games.rps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {
    @Id
    private final String id;
    private List<Move> moves = new ArrayList<>();
    private GameStatus status = GameStatus.IN_PROGRESS;

    @JsonIgnore
    @Version
    private Long version;

    public void addMove(Move move) {
        moves.add(move);
    }

    public GameStatistics getStatistics() {
        long total = moves.size();
        long wins = getCount(MoveResult.WIN);
        long draws = getCount(MoveResult.DRAW);
        long looses = getCount(MoveResult.LOOSE);

        return new GameStatistics(total, wins, draws, looses);
    }

    private long getCount(MoveResult result) {
        return moves.stream().filter(m -> m.getResult() == result).count();
    }
}
