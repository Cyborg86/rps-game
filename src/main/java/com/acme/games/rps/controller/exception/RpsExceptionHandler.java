package com.acme.games.rps.controller.exception;

import com.acme.games.rps.exception.GameAlreadyFinishedException;
import com.acme.games.rps.exception.GameNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RpsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralError(Exception e, WebRequest request) {
        return handleExceptionInternal(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<Object> handleGameNotFound(GameNotFoundException e, WebRequest request) {
        return handleExceptionInternal(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameAlreadyFinishedException.class)
    public ResponseEntity<Object> handleGameAlreadyFinished(GameAlreadyFinishedException e, WebRequest request) {
        return handleExceptionInternal(e, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, WebRequest request, HttpStatus internalServerError) {
        return handleExceptionInternal(e, null, new HttpHeaders(), internalServerError, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (status.is5xxServerError()) {
            log.error("Error occurred while processing request: {}", request, ex);
        } else {
            log.debug("Invalid client request: {}", request, ex);
        }

        ErrorResponse response = new ErrorResponse(ex.getClass().getName(), ex.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}

