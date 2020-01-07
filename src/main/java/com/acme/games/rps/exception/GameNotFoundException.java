package com.acme.games.rps.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException() {
    }

    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameNotFoundException(Throwable cause) {
        super(cause);
    }
}
