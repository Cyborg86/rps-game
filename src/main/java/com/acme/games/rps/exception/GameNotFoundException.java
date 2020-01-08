package com.acme.games.rps.exception;

import static java.lang.String.format;

public class GameNotFoundException extends RpsGameException {
    public GameNotFoundException(String id) {
        super(format("Game Id=%s not found", id));
    }
}
