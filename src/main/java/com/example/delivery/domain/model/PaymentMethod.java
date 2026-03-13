package com.example.delivery.domain.model;

public enum PaymentMethod {
    EFECTIVO("Efectivo"),
    TARJETA("Tarjeta");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

