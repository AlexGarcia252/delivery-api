package com.example.delivery.application.service;

import com.example.delivery.domain.model.Order;
import com.example.delivery.domain.port.in.IOrden.ICreateOrderInteractor;
import com.example.delivery.domain.port.in.IOrden.IDeleteOrderInteractor;
import com.example.delivery.domain.port.out.ClientRepositoryPort;
import com.example.delivery.domain.port.out.OrderRepositoryPort;
import com.example.delivery.domain.port.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrdenInteractorService implements
        ICreateOrderInteractor,
        IDeleteOrderInteractor
{
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

    @Override
    public void deleteOrder(UUID uuid) {
        if(!orderRepositoryPort.existsByUUID(uuid)){
            throw new IllegalArgumentException("No se puede eliminar: La orden con UUID " + uuid + " no existe.");
        }
        orderRepositoryPort.deleteOrder(uuid);
    }

}
