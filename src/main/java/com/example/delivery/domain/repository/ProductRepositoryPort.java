package com.example.delivery.domain.repository;

import com.example.delivery.domain.model.Product;

public interface ProductRepositoryPort {
    void createProduct(Product product);
}
