package com.example.delivery.infrastructure.database.h2.repository;

import com.example.delivery.infrastructure.database.h2.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity,Long> {
    boolean existsByName(String name);
}
