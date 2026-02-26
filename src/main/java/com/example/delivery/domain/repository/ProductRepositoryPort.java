package com.example.delivery.domain.repository;

import com.example.delivery.domain.model.Product;

public interface ProductRepositoryPort {
    Product createProduct(Product product);
    boolean existsByName(String name);
}
