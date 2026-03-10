package com.example.delivery.domain.port.in.iClient;

import com.example.delivery.domain.model.Client;

public interface IUpdateClientInteractor {
    Client updateClient(String documen , Client client);
}
