package com.example.delivery.infrastructure.rest.controller;

import com.example.delivery.application.ICreateProductInteractor;
import com.example.delivery.domain.model.Product;
import com.example.delivery.infrastructure.rest.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ICreateProductInteractor createProductInteractor;
    private final ProductDtoMapper productDtoMapper;
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {

        Product productToCreate = productDtoMapper.toDomain(requestDto);
        Product createdProduct = createProductInteractor.createProduct(productToCreate);
        ProductResponseDto response = productDtoMapper.toDto(createdProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
