package com.example.delivery.application;

import com.example.delivery.domain.model.Product;

import java.util.UUID;


public interface IGetProductByUuidInteractor {
    Product execute(UUID uuid);
}
