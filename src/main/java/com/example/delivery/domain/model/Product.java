package com.example.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private UUID uuid;
    private String name;
    private Category category;
    private String description;
    private BigDecimal basePrice;
    private boolean available;

    public void validateAndFormat(){

        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }

        if (this.name != null) {
            this.name = this.name.toUpperCase().trim();
        }


        if (this.basePrice == null || this.basePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser un número positivo mayor a cero.");
        }

        if (this.name != null && this.name.length() > 255) {
            throw new IllegalArgumentException("El nombre no puede superar los 255 caracteres.");
        }
        if (this.description != null && this.description.length() > 511) {
            throw new IllegalArgumentException("La descripción no puede superar los 511 caracteres.");
        }
    }


}
