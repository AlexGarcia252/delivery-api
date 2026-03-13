package com.example.delivery.domain.exception.order;

public class OrderNotFoundException  extends RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
