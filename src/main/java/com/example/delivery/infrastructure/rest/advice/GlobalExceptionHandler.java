package com.example.delivery.infrastructure.rest.advice;

import com.example.delivery.domain.exception.client.ClientConflictException;
import com.example.delivery.domain.exception.client.ClientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<?> handleClientNotFound(ClientNotFoundException mensaje) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status",HttpStatus.NOT_FOUND,
                        "error", "Client not found",
                        "message", mensaje.getMessage()
                ));
    }

    @ExceptionHandler(ClientConflictException.class)
    public ResponseEntity<?> handleClienConflictException(ClientConflictException mensaje) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "status",HttpStatus.CONFLICT,
                        "error", "Client CONFLICT",
                        "message", mensaje.getMessage()
                ));
    }
}
