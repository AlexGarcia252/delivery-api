package com.example.delivery.application;

import com.example.delivery.domain.model.Product;
import com.example.delivery.domain.repository.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@RequiredArgsConstructor
@Component
public class GetProductByUuidInteractor implements IGetProductByUuidInteractor {

    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public Product execute(UUID uuid) {
        return productRepositoryPort.getProductByUuid(uuid);
    }
}
