package com.example.delivery.infrastructure.rest.controller;

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
public class ProductResponseDto {
    private UUID uuid;
    private String fantasyName;
    private String category;
    private String description;
    private BigDecimal price;
    private Boolean available;
}
