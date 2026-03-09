package com.example.delivery.domain.port.in.IOrden;

import com.example.delivery.domain.model.Order;

public interface ICreateOrderInteractor {
    Order createOrder(Order order);
}

