package com.example.delivery.domain.exception.client;

public class InvalidClientDocumentException extends ClientValidationException {
    public InvalidClientDocumentException(String message) {
        super(message);
    }
}

