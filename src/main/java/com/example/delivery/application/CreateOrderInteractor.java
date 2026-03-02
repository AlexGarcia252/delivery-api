package com.example.delivery.application;

import com.example.delivery.domain.model.Order;
import com.example.delivery.domain.repository.ClientRepositoryPort;
import com.example.delivery.domain.repository.OrderRepositoryPort;
import com.example.delivery.domain.repository.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateOrderInteractor implements ICreateOrderInteractor {

    private final OrderRepositoryPort orderRepositoryPort;
    private final ClientRepositoryPort clientRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public Order createOrder(Order order) {
        // Validar que el cliente existe
        if (!clientRepositoryPort.existsByDocument(order.getClientDocument())) {
            throw new IllegalArgumentException("El cliente con documento " + order.getClientDocument() + " no existe");
        }

        // Validar que el producto existe
        if (productRepositoryPort.getProductByUuid(order.getProductUuid()) == null) {
            throw new IllegalArgumentException("El producto con UUID " + order.getProductUuid() + " no existe");
        }

        // Obtener el precio actual del producto para la orden
        var product = productRepositoryPort.getProductByUuid(order.getProductUuid());
        order.setPrice(product.getBasePrice());

        // Validar y calcular campos derivados
        order.validateAndFormat();

        // Persistir la orden en BD
        return orderRepositoryPort.createOrder(order);
    }
}


