package com.example.delivery.infrastructure.database.h2.adapter;

import com.example.delivery.domain.exception.client.ClientNotFoundException;
import com.example.delivery.domain.model.Client;
import com.example.delivery.domain.port.out.ClientRepositoryPort;
import com.example.delivery.infrastructure.database.h2.entity.ClientEntity;
import com.example.delivery.infrastructure.database.h2.mapper.ClientMapper;
import com.example.delivery.infrastructure.database.h2.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client createClient(Client client) {
        ClientEntity clientEntity = clientMapper.toEntity(client);
        ClientEntity saved = clientJpaRepository.save(clientEntity);
        return clientMapper.toDomain(saved);
    }

    @Override
    public boolean existsByDocument(String document) {
        return clientJpaRepository.existsByDocument(document);
    }

    @Override
    public Client getClientById(String id) {
        ClientEntity clientEntity = clientJpaRepository.findByDocument(id).orElseThrow(
                ()-> new ClientNotFoundException("No existe este documento")
        );
        return clientMapper.toDomain(clientEntity);
    }
    @Override
    public Client updateClient(String document, Client client) {
        ClientEntity entity = clientJpaRepository.findByDocument(document).orElseThrow(()->
                new ClientNotFoundException("No existe este documento")//manejar excepcion correspondinete
        );

        clientMapper.updateEntityFromDomain(client, entity);
        return clientMapper.toDomain(clientJpaRepository.save(entity));
    }


    @Override
    @Transactional
    public void deleteClient(String id){
        ClientEntity clientEntity = clientJpaRepository.findByDocument(id).orElseThrow(
                ()-> new  IllegalArgumentException("No existe este documento")
        );
        clientJpaRepository.delete(clientEntity);
    }
}
