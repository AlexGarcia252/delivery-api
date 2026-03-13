package com.example.delivery.infrastructure.rest.mapper;

import com.example.delivery.domain.model.Category;
import com.example.delivery.domain.model.Product;
import com.example.delivery.infrastructure.rest.dto.request.ProductRequestDto;
import com.example.delivery.infrastructure.rest.dto.response.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    @Mapping(target = "uuid", ignore = true)
    @Mapping(source = "fantasyName", target = "name")
    @Mapping(source = "price", target = "basePrice")
    @Mapping(source = "category", target = "category", qualifiedByName = "stringToCategory")
    Product toDomain(ProductRequestDto dto);

    @Mapping(source = "name", target = "fantasyName")
    @Mapping(source = "basePrice", target = "price")
    @Mapping(source = "category", target = "category", qualifiedByName = "categoryToString")
    ProductResponseDto toDto(Product domain);

    @Named("stringToCategory")
    default Category stringToCategory(String category) {
        return category == null ? null : Category.valueOf(category.trim().toUpperCase());
    }

    @Named("categoryToString")
    default String categoryToString(Category category) {
        return category == null ? null : category.name();
    }
}
