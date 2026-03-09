package com.example.delivery.domain.port.in.IOrden;

import java.util.UUID;

public interface IDeleteOrderInteractor {
    void deleteOrder(UUID uuid);
}
