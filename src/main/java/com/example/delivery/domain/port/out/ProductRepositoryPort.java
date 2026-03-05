package com.example.delivery.domain.port.out;

import com.example.delivery.domain.model.Product;

import java.util.UUID;

public interface ProductRepositoryPort {
    Product createProduct(Product product);
    boolean existsByName(String name);
    Product getProductByUuid(UUID uuid);
}
