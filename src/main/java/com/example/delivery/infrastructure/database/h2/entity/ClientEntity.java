package com.example.delivery.infrastructure.database.h2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false, length = 20, unique = true)
    private String document;

    @Column(nullable = false)
    private String nameAndSurname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, length = 500)
    private String shippingAddress;

    private boolean deleted = false;

    //@OneToMany(
    //        mappedBy = "client",
    //        orphanRemoval = true,
    //        cascade = CascadeType.ALL
    //)
    //private Set<Order> orders = new HashSet<>();

    public ClientEntity(String document, String nameAndSurname, String email, String phoneNumber, String shippingAddress) {
        this.document = document;
        this.nameAndSurname = nameAndSurname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
    }

}
