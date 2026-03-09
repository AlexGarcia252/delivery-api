package com.example.delivery.domain.port.out;

import com.example.delivery.domain.model.Client;

public interface ClientRepositoryPort {
    Client createClient(Client client);
    boolean existsByDocument(String document);
    Client getClientById(String id);
    Client updateClient(String document, Client client);
    void deleteClient(String id);

}
