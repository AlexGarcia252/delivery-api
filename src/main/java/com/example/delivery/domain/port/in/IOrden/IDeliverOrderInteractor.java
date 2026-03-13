package com.example.delivery.domain.port.in.IOrden;

import com.example.delivery.domain.model.Order;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IDeliverOrderInteractor
{
    Order deliverOrder(UUID uuid, LocalDateTime deliveredDate);
}
