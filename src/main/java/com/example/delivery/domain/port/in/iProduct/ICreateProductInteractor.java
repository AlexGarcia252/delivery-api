package com.example.delivery.domain.port.in.iProduct;

import com.example.delivery.domain.model.Product;

public interface ICreateProductInteractor {
    Product createProduct(Product product);
}
