package com.example.delivery.infrastructure.database.h2.adapter;

import com.example.delivery.domain.model.Client;
import com.example.delivery.domain.repository.ClientRepositoryPort;
import com.example.delivery.infrastructure.database.h2.entity.ClientEntity;
import com.example.delivery.infrastructure.database.h2.mapper.ClientMapper;
import com.example.delivery.infrastructure.database.h2.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client createClient(Client client) {
        ClientEntity clientEntity = clientMapper.toEntity(client);
        ClientEntity saved = clientJpaRepository.save(clientEntity);
        return ClientMapper.toDomain(saved);
    }

    @Override
    public boolean existsByDocument(String document) {
        return clientJpaRepository.existsByDocument(document);
    }

    @Override
    public Client getClientById(String id) {
        ClientEntity clientEntity = clientJpaRepository.findByDocument(id);
        if (clientEntity == null) {
            return null;
        }
        Client client = ClientMapper.toDomain(clientEntity);
        return client;
    }
}
