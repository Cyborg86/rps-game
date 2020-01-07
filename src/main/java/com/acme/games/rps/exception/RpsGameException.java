package com.acme.games.rps.exception;

public class RpsGameException extends RuntimeException {
    public RpsGameException() {
    }

    public RpsGameException(String message) {
        super(message);
    }

    public RpsGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpsGameException(Throwable cause) {
        super(cause);
    }
}
