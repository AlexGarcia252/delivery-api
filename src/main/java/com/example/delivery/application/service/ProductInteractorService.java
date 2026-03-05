package com.example.delivery.application.service;

import com.example.delivery.domain.model.Product;
import com.example.delivery.domain.port.in.iProduct.ICreateProductInteractor;
import com.example.delivery.domain.port.in.iProduct.IGetProductByUuidInteractor;
import com.example.delivery.domain.port.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductInteractorService implements
        ICreateProductInteractor,
        IGetProductByUuidInteractor {

    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public Product createProduct(Product product) {
        product.validateAndFormat();

        if (productRepositoryPort.existsByName(product.getName())) {
            throw new IllegalArgumentException("El producto ya existe");
        }
        return productRepositoryPort.createProduct(product);
    }


    @Override
    public Product execute(UUID uuid) {
        return productRepositoryPort.getProductByUuid(uuid);
    }
}
