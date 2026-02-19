package com.example.delivery.infrastructure.database.h2.mapper;

import com.example.delivery.domain.model.Client;
import com.example.delivery.infrastructure.database.h2.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public static Client toDomain(ClientEntity clientEntity) {
        return new Client(
                //clientEntity.getClientId(),
                clientEntity.getDocument(),
                clientEntity.getNameAndSurname(),
                clientEntity.getEmail(),
                clientEntity.getPhoneNumber(),
                clientEntity.getShippingAddress()

        );
    }

    public ClientEntity toEntity(Client domain) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setDocument(domain.getDocument());
        clientEntity.setNameAndSurname(domain.getNameAndSurname());
        clientEntity.setEmail(domain.getEmail());
        clientEntity.setPhoneNumber(domain.getPhoneNumber());
        clientEntity.setShippingAddress(domain.getShippingAddress());
        return clientEntity;
    }


}
