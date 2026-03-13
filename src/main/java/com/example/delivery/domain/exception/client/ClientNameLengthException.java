package com.example.delivery.domain.exception.client;

public class ClientNameLengthException extends ClientValidationException {
    public ClientNameLengthException(String message) {
        super(message);
    }
}

