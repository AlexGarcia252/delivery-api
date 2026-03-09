package com.example.delivery.application;

import com.example.delivery.domain.model.Order;

public interface ICreateOrderInteractor {
    Order createOrder(Order order);
}

