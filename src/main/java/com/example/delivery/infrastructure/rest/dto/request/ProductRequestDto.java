package com.example.delivery.infrastructure.rest.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 255, message = "El nombre del producto no puede superar 255 caracteres")
    private String fantasyName;

    @NotBlank(message = "La categoria es obligatoria")
    @Pattern(
            regexp = "^(HAMBURGERS_AND_HOTDOGS|CHICKEN|FISH|MEATS|DESSERTS|VEGAN_FOOD|KIDS_MEALS)$",
            message = "La categoria no es valida"
    )
    private String category;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 511, message = "La descripcion no puede superar 511 caracteres")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private BigDecimal price;

    @NotNull(message = "La disponibilidad es obligatoria")
    private Boolean available;
}
