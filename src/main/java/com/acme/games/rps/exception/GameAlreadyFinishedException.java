package com.acme.games.rps.exception;

public class GameAlreadyFinishedException extends RpsGameException {
    public GameAlreadyFinishedException() {
    }

    public GameAlreadyFinishedException(String message) {
        super(message);
    }

    public GameAlreadyFinishedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameAlreadyFinishedException(Throwable cause) {
        super(cause);
    }
}
