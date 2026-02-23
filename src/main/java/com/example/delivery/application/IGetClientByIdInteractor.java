package com.example.delivery.application;

import com.example.delivery.domain.model.Client;

public interface IGetClientByIdInteractor {
    Client execute(String document);
}
