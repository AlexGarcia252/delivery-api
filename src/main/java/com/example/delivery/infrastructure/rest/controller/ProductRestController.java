package com.example.delivery.infrastructure.rest.controller;

import com.example.delivery.domain.port.in.iProduct.ICreateProductInteractor;
import com.example.delivery.domain.port.in.iProduct.IGetProductByUuidInteractor;
import com.example.delivery.domain.model.Product;
import com.example.delivery.infrastructure.rest.dto.request.ProductRequestDto;
import com.example.delivery.infrastructure.rest.dto.response.ProductResponseDto;
import com.example.delivery.infrastructure.rest.mapper.ProductDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "Productos", description = "Gestión de productos en el sistema de entregas")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ICreateProductInteractor createProductInteractor;
    private final IGetProductByUuidInteractor getProductByUuidInteractor;
    private final ProductDtoMapper productDtoMapper;

    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el sistema. El nombre debe ser único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un producto con ese nombre")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        Product productToCreate = productDtoMapper.toDomain(requestDto);
        Product createdProduct = createProductInteractor.createProduct(productToCreate);
        ProductResponseDto response = productDtoMapper.toDto(createdProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener producto por UUID", description = "Recupera la información completa de un producto usando su identificador único UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponseDto> getProductByUuid(@PathVariable UUID uuid) {
        Product product = getProductByUuidInteractor.execute(uuid);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        ProductResponseDto response = productDtoMapper.toDto(product);
        return ResponseEntity.ok(response);
    }
}
