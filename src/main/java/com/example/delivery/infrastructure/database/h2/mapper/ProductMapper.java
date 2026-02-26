package com.example.delivery.infrastructure.database.h2.mapper;

import com.example.delivery.domain.model.Product;
import com.example.delivery.infrastructure.database.h2.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductEntity toEntity(Product product){
        ProductEntity entity= new ProductEntity();
        entity.setUuid(product.getUuid());
        entity.setName(product.getName());
        entity.setCategory(product.getCategory());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getBasePrice());
        entity.setAvailable(product.isAvailable());
        return  entity;
    }
    public Product toDomain(ProductEntity productEntity){
        if (productEntity == null) return null;
        Product product= new Product();
        product.setUuid(productEntity.getUuid());
        product.setName(productEntity.getName());
        product.setCategory(productEntity.getCategory());
        product.setDescription(productEntity.getDescription());
        product.setBasePrice(productEntity.getPrice());
        product.setAvailable(productEntity.isAvailable());
        return product;
    }
}
