package com.acme.games.rps.controller.exception;

import lombok.Data;

@Data
class ErrorResponse {
    private final String errorType;
    private final String message;
}
