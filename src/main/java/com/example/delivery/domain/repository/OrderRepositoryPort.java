package com.example.delivery.domain.repository;

import com.example.delivery.domain.model.Order;

import java.util.UUID;

public interface OrderRepositoryPort {
    Order createOrder(Order order);
    Order getOrderByUuid(UUID uuid);
}

