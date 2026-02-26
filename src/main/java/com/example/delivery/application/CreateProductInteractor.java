package com.example.delivery.application;

import com.example.delivery.domain.model.Product;
import com.example.delivery.domain.repository.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateProductInteractor implements ICreateProductInteractor{
    private final ProductRepositoryPort productRepositoryPort;
    @Override
    public Product createProduct(Product product) {
        product.validateAndFormat();

        if (productRepositoryPort.existsByName(product.getName())) {
            throw new IllegalArgumentException("El producto ya existe");
        }
        return productRepositoryPort.createProduct(product);
    }
}
