package com.example.delivery.infrastructure.database.h2.adapter;

import com.example.delivery.domain.model.Product;
import com.example.delivery.domain.port.out.ProductRepositoryPort;
import com.example.delivery.infrastructure.database.h2.entity.ProductEntity;
import com.example.delivery.infrastructure.database.h2.mapper.ProductMapper;
import com.example.delivery.infrastructure.database.h2.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {
    private final ProductJpaRepository productJpaRepository;
    private final ProductMapper productMapper;

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        ProductEntity saved = productJpaRepository.save(productEntity);
        return productMapper.toDomain(saved);
    }

    @Override
    public boolean existsByName(String name) {
        return productJpaRepository.existsByName(name);
    }


    @Override
    public Product getProductByUuid(UUID uuid) {
        ProductEntity productEntity = productJpaRepository.findByUuid(uuid);
        if (productEntity == null) {
            return null;
        }
        return productMapper.toDomain(productEntity);
    }
}