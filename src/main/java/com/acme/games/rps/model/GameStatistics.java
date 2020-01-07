package com.acme.games.rps.model;

import lombok.Data;

@Data
public class GameStatistics {
    private final long total;
    private final long wins;
    private final long draws;
    private final long looses;
}
