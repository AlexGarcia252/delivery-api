package com.example.delivery.domain.exception.client;

public class ClientShippingAddressLengthException extends ClientValidationException {
    public ClientShippingAddressLengthException(String message) {
        super(message);
    }
}

