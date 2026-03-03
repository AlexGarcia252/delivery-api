package com.example.delivery.application;

import com.example.delivery.domain.model.Client;
import com.example.delivery.domain.repository.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteClientInteractor implements IDeleteClientInteractor{

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Client deleteClient(Client client){
        if(clientRepositoryPort.existsByDocument(client.getDocument())){
            return clientRepositoryPort.deleteClient(client);
        }
        else {
            return null;
        }
    }
}
