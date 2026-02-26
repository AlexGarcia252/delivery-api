package com.example.delivery.infrastructure.rest.mapper;

import com.example.delivery.domain.model.Category;
import com.example.delivery.domain.model.Product;
import com.example.delivery.infrastructure.rest.controller.ProductRequestDto;
import com.example.delivery.infrastructure.rest.controller.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    public Product toDomain(ProductRequestDto dto) {

        return new Product(
                null,
                dto.getFantasyName(),
                Category.valueOf(dto.getCategory()),
                dto.getDescription(),
                dto.getPrice(),
                dto.getAvailable()
        );
    }
    public ProductResponseDto toDto(Product domain) {
        return new ProductResponseDto(
                domain.getUuid(),
                domain.getName(),
                domain.getCategory().name(),
                domain.getDescription(),
                domain.getBasePrice(),
                domain.isAvailable()
        );
    }
}
