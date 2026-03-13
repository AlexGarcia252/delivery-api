package com.example.delivery.domain.port.out;

import com.example.delivery.domain.model.Order;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderRepositoryPort {
    Order createOrder(Order order);
    Order getOrderByUuid(UUID uuid);
    void deleteOrder(UUID uuid);
    boolean existsByUUID(UUID uuid);
    Order deliverOrder(UUID uuid, LocalDateTime deliveredDate);
}

