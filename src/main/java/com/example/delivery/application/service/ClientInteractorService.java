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

        Client clientbd = clientRepositoryPort.getClientById(documen);

        if(!clientRepositoryPort.existsByDocument(documen)){
            throw  new ClientNotFoundException("no se encontro el documento"+documen);
        }
        if (clientbd.getNameAndSurname().equals(client.getNameAndSurname()) &&
                        clientbd.getEmail().equals(client.getEmail()) &&
                        clientbd.getPhoneNumber().equals(client.getPhoneNumber()) &&
                        clientbd.getShippingAddress().equals(client.getShippingAddress())
        ) {
            System.out.println("estoy aca");
            throw new ClientConflictException("No se detectaron cambios");
        }
        return clientRepositoryPort.updateClient(documen, client );
    }
}
