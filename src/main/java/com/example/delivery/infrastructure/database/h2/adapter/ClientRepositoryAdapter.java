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
        return ClientMapper.toDomain(saved);
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
        if (clientEntity == null) {
            return null;
        }
        Client client = ClientMapper.toDomain(clientEntity);
        return client;
    }
    @Override
    public Client updateClient(String document, Client client) {
        ClientEntity entity = clientJpaRepository.findByDocument(document).orElseThrow(()->
                new ClientNotFoundException("No existe este documento")//manejar excepcion correspondinete
        );

        ClientEntity mappedEntity = clientMapper.toEntityUpdate(entity,client); //TODO: implementar mapper de entidad a pojo con los nuevos datos
        return ClientMapper.toDomain(clientJpaRepository.save(mappedEntity));
    }


    @Override
    @Transactional
    public void deleteClient(String id){
        ClientEntity clientEntity = clientJpaRepository.findByDocument(id);
        clientJpaRepository.delete(clientEntity);
    }
}
