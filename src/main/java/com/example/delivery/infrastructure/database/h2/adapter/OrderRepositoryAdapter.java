package com.example.delivery.infrastructure.database.h2.adapter;

import com.example.delivery.domain.model.Order;
import com.example.delivery.domain.repository.OrderRepositoryPort;
import com.example.delivery.infrastructure.database.h2.entity.OrderEntity;
import com.example.delivery.infrastructure.database.h2.mapper.OrderMapper;
import com.example.delivery.infrastructure.database.h2.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@RequiredArgsConstructor
@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper orderMapper;

    // Crear nueva orden en BD
    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = orderMapper.toEntity(order);
        OrderEntity saved = orderJpaRepository.save(orderEntity);
        return orderMapper.toDomain(saved);
    }

    // Obtener orden por UUID
    @Override
    public Order getOrderByUuid(UUID uuid) {
        OrderEntity orderEntity = orderJpaRepository.findByUuid(uuid);
        if (orderEntity == null) {
            return null;
        }
        return orderMapper.toDomain(orderEntity);
    }
}


