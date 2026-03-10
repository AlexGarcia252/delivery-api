package com.example.delivery.application.service;

import com.example.delivery.domain.exception.product.ProductNotFoundException;
import com.example.delivery.domain.model.Category;
import com.example.delivery.domain.model.Product;
import com.example.delivery.domain.port.in.iProduct.ICreateProductInteractor;
import com.example.delivery.domain.port.in.iProduct.IDeleteProductInteractor;
import com.example.delivery.domain.port.in.iProduct.IGetProductByUuidInteractor;
import com.example.delivery.domain.port.in.iProduct.IUpdateProductInteractor;
import com.example.delivery.domain.port.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductInteractorService implements
        ICreateProductInteractor,
        IGetProductByUuidInteractor,
        IDeleteProductInteractor,
        IUpdateProductInteractor {

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

    @Override
    public void updateProduct(UUID id,
                              String name,
                              Category category,
                              String description,
                              BigDecimal price,
                              boolean available) {
        productRepositoryPort.updateProduct(id,
                name,
                category,
                description,
                price,
                available);
    }

    @Override
    @Transactional
    public boolean deleteProduct(UUID uuid) {
        if (!productRepositoryPort.existsByUUID(uuid)) {
            throw new ProductNotFoundException("No se puede eliminar: el Producto no existe.");
        }
        productRepositoryPort.deleteByUuid(uuid);
        return true;
    }

    @Override
    public boolean IvalidationUUid(UUID uuid) {
        String uuidLLegado = uuid.toString();
        try {
            java.util.UUID.fromString(uuidLLegado);
            return true;
        }catch (IllegalArgumentException e){
            System.out.println(e);
            return false;
        }
    }
}
