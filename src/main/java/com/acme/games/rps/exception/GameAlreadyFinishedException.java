package com.acme.games.rps.exception;

import static java.lang.String.format;

public class GameAlreadyFinishedException extends RpsGameException {
    public GameAlreadyFinishedException(String gameId) {
        super(format("Game Id=%s is already finished", gameId));
    }
}
