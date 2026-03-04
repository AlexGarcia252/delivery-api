package com.example.delivery.application;

import com.example.delivery.domain.model.Category;

import java.math.BigDecimal;
import java.util.UUID;

public interface IUpdateProductInteractor {
    void updateProduct(UUID id,
                       String name,
                       Category category,
                       String description,
                       BigDecimal price,
                       boolean available);
}
