package com.example.delivery.infrastructure.database.h2.mapper;

import com.example.delivery.domain.model.Product;
import com.example.delivery.infrastructure.database.h2.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "basePrice", target = "price")
    ProductEntity toEntity(Product product);

    @Mapping(source = "price", target = "basePrice")
    Product toDomain(ProductEntity productEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(source = "basePrice", target = "price")
    void updateEntityFromDomain(Product product, @MappingTarget ProductEntity entity);
}
