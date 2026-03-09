package com.example.delivery.application;

import com.example.delivery.domain.repository.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteClientInteractor implements IDeleteClientInteractor{

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public void deleteClient(String id){
        if(clientRepositoryPort.existsByDocument(id)){
            clientRepositoryPort.deleteClient(id);
        }
    }
}
