package com.example.delivery.infrastructure.database.h2.repository;

import com.example.delivery.infrastructure.database.h2.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByUuid(UUID uuid);
}

