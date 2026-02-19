package com.example.delivery.application;

import com.example.delivery.domain.model.Client;
import com.example.delivery.domain.repository.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateClientInteractor implements ICreateClientInteractor {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Client createClient(Client client) {
        if (clientRepositoryPort.existsByDocument(client.getDocument())) {
            throw new IllegalArgumentException("Ya existe un cliente con el documento: " + client.getDocument());
        }
        return clientRepositoryPort.createClient(client);
    }
}
