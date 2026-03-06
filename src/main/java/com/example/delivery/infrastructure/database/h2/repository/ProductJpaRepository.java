package com.example.delivery.infrastructure.database.h2.repository;

import com.example.delivery.infrastructure.database.h2.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductEntity,Long> {
    boolean existsByName(String name);
    ProductEntity findByUuid(UUID uuid);
    boolean existsByUuid(UUID uuid);
    void deleteByUuid (UUID uuid);
}
