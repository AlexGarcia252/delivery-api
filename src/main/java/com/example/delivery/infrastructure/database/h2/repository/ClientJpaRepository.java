package com.example.delivery.infrastructure.database.h2.repository;

import com.example.delivery.infrastructure.database.h2.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {

    boolean existsByDocument(String document);
    ClientEntity findByDocument(String document);
}
