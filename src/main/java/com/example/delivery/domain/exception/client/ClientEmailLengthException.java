package com.example.delivery.domain.exception.client;

public class ClientEmailLengthException extends ClientValidationException {
    public ClientEmailLengthException(String message) {
        super(message);
    }
}

