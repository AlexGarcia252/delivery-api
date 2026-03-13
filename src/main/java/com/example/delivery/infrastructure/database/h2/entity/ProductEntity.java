package com.example.delivery.infrastructure.database.h2.entity;

import com.example.delivery.domain.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;
    private BigDecimal price;
    private boolean available;


}
