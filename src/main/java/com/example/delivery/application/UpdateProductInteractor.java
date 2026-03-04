package com.example.delivery.application;

import com.example.delivery.domain.model.Category;
import com.example.delivery.domain.repository.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UpdateProductInteractor implements IUpdateProductInteractor{

    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public void updateProduct(UUID id,
                              String name,
                              Category category,
                              String description,
                              BigDecimal price,
                              boolean available) {
        productRepositoryPort.updateProduct(id,
                name,
                category,
                description,
                price,
                available);
    }
}
