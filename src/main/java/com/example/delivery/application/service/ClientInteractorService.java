package com.example.delivery.application.service;

import com.example.delivery.domain.exception.client.ClientConflictException;
import com.example.delivery.domain.exception.client.ClientNotFoundException;
import com.example.delivery.domain.model.Client;
import com.example.delivery.domain.port.in.iClient.ICreateClientInteractor;
import com.example.delivery.domain.port.in.iClient.IGetClientByIdInteractor;
import com.example.delivery.domain.port.in.iClient.IUpdateClientInteractor;
import com.example.delivery.domain.port.out.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ClientInteractorService implements
        ICreateClientInteractor,
        IGetClientByIdInteractor,
        IUpdateClientInteractor {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Client createClient(Client client) {
        if (clientRepositoryPort.existsByDocument(client.getDocument())) {
            throw new IllegalArgumentException("Ya existe un cliente con el documento: " + client.getDocument());
        }
        return clientRepositoryPort.createClient(client);
    }

    @Override
    public Client execute(String document) {
        return clientRepositoryPort.getClientById(document);
    }

    @Override
    public Client updateClient(String documen, Client client) {
        if(!clientRepositoryPort.existsByDocument(documen)){
            throw  new ClientNotFoundException("no se encontro el puto documento"+documen);
        }
        if (
                client.getNameAndSurname().equals(client.getNameAndSurname()) &&
                        client.getEmail().equals(client.getEmail()) &&
                        client.getPhoneNumber().equals(client.getPhoneNumber()) &&
                        client.getShippingAddress().equals(client.getShippingAddress())
        ) {
            throw new ClientConflictException("No se detectaron cambios");
        }
        return clientRepositoryPort.updateClient(documen, client );
    }
}
