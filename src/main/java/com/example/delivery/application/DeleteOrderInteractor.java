package com.example.delivery.application;

import com.example.delivery.domain.repository.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@RequiredArgsConstructor
public class DeleteOrderInteractor implements IDeleteOrderInteractor{
    private final OrderRepositoryPort orderRepositoryPort;
    @Override
    public void deleteOrder(UUID uuid) {
        if(!orderRepositoryPort.existsByUUID(uuid)){
            throw new IllegalArgumentException("No se puede eliminar: La orden con UUID " + uuid + " no existe.");
        }
        orderRepositoryPort.deleteOrder(uuid);
    }

}
