package com.example.delivery.application;

import com.example.delivery.domain.model.Client;
import com.example.delivery.domain.repository.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetClientByIdInteractor implements IGetClientByIdInteractor {

    private final ClientRepositoryPort clientRepositoryPort;

    public Client execute(String document) {
        return clientRepositoryPort.getClientById(document);
    }
}
