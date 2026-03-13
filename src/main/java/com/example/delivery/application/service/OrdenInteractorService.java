package com.example.delivery.application.service;

import com.example.delivery.domain.exception.client.ClientNotFoundException;
import com.example.delivery.domain.exception.order.OrderNotFoundException;
import com.example.delivery.domain.exception.product.ProductNotFoundException;
import com.example.delivery.domain.model.Order;
import com.example.delivery.domain.port.in.IOrden.ICreateOrderInteractor;
import com.example.delivery.domain.port.in.IOrden.IDeleteOrderInteractor;
import com.example.delivery.domain.port.in.IOrden.IDeliverOrderInteractor;
import com.example.delivery.domain.port.out.ClientRepositoryPort;
import com.example.delivery.domain.port.out.OrderRepositoryPort;
import com.example.delivery.domain.port.out.ProductRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class OrdenInteractorService implements
        ICreateOrderInteractor,
        IDeleteOrderInteractor,
        IDeliverOrderInteractor
{
    private final OrderRepositoryPort orderRepositoryPort;
    private final ClientRepositoryPort clientRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public Order createOrder(Order order) {
        // Validar que el cliente existe
        if (!clientRepositoryPort.existsByDocument(order.getClientDocument())) {
            throw new ClientNotFoundException("El cliente con documento " + order.getClientDocument() + " no existe");
        }

        // Validar que el producto existe
        if (productRepositoryPort.getProductByUuid(order.getProductUuid()) == null) {
            throw new ProductNotFoundException("El producto con UUID " + order.getProductUuid() + " no existe");
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
        if (!orderRepositoryPort.existsByUUID(uuid)) {
            throw new OrderNotFoundException("La orden con UUID " + uuid + " no existe");
        }
        orderRepositoryPort.deleteOrder(uuid);
    }

    @Override
    public Order deliverOrder(UUID uuid, LocalDateTime deliveredDate) {
        if (!orderRepositoryPort.existsByUUID(uuid)) {
            throw new OrderNotFoundException("La orden con UUID " + uuid + " no existe");
        }
        return orderRepositoryPort.deliverOrder(uuid, deliveredDate);
    }
}
