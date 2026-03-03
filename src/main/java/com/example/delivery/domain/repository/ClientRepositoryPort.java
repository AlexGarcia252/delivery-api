package com.example.delivery.domain.repository;

import com.example.delivery.domain.model.Client;

public interface ClientRepositoryPort {
    Client createClient(Client client);
    boolean existsByDocument(String document);
    Client getClientById(String id);
    Client deleteClient(Client client);

}
