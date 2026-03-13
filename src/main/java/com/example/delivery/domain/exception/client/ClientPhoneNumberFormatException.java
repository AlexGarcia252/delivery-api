package com.example.delivery.domain.exception.client;

public class ClientPhoneNumberFormatException extends ClientValidationException {
    public ClientPhoneNumberFormatException(String message) {
        super(message);
    }
}

