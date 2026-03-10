package com.example.delivery.infrastructure.rest.dto.request;

import com.example.delivery.domain.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String fantasyName;
    @NotBlank
    private String category;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Boolean available;
}
