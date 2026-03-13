package com.example.delivery.infrastructure.database.h2.adapter;

import com.example.delivery.domain.model.Order;

import com.example.delivery.domain.port.out.OrderRepositoryPort;
import com.example.delivery.infrastructure.database.h2.entity.OrderEntity;
import com.example.delivery.infrastructure.database.h2.mapper.OrderMapper;
import com.example.delivery.infrastructure.database.h2.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    //Eliminar orden por UUID
    @Override
    public void deleteOrder(UUID uuid) {
        OrderEntity orderEntity = orderJpaRepository.findByUuid(uuid);
        //si no encuentra esa orden en la bd, devuelve null
        if (orderEntity != null) {
            orderJpaRepository.delete(orderEntity);
        }
    }
    //Verificar si esa orden existe por UUID
    @Override
    public boolean existsByUUID(UUID uuid) {
        return orderJpaRepository.existsByUuid(uuid);
    }

    @Override
    public Order deliverOrder(UUID uuid, LocalDateTime deliveredDate) {
        OrderEntity orderEntity = orderJpaRepository.findByUuid(uuid);
        if (orderEntity == null) {
            return null;
        }
        orderEntity.setDelivered(true);
        orderEntity.setDeliveredDate(deliveredDate);
        OrderEntity saved = orderJpaRepository.save(orderEntity);
        return orderMapper.toDomain(saved);
    }
}


