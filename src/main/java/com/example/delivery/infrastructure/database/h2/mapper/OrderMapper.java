package com.example.delivery.infrastructure.database.h2.mapper;

import com.example.delivery.domain.model.Order;
import com.example.delivery.infrastructure.database.h2.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderEntity toEntity(Order order) {
        if (order == null) return null;

        OrderEntity entity = new OrderEntity();
        entity.setUuid(order.getUuid());
        entity.setCreationDateTime(order.getCreationDateTime());
        entity.setClientDocument(order.getClientDocument());
        entity.setProductUuid(order.getProductUuid());
        entity.setQuantity(order.getQuantity());
        entity.setExtraInformation(order.getExtraInformation());
        entity.setPrice(order.getPrice());
        entity.setSubTotal(order.getSubTotal());
        entity.setTax(order.getTax());
        entity.setGrandTotal(order.getGrandTotal());
        entity.setPaymentMethod(order.getPaymentMethod());
        entity.setDelivered(order.isDelivered());
        entity.setDeliveredDate(order.getDeliveredDate());

        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        if (entity == null) return null;

        Order order = new Order();
        order.setUuid(entity.getUuid());
        order.setCreationDateTime(entity.getCreationDateTime());
        order.setClientDocument(entity.getClientDocument());
        order.setProductUuid(entity.getProductUuid());
        order.setQuantity(entity.getQuantity());
        order.setExtraInformation(entity.getExtraInformation());
        order.setPrice(entity.getPrice());
        order.setSubTotal(entity.getSubTotal());
        order.setTax(entity.getTax());
        order.setGrandTotal(entity.getGrandTotal());
        order.setPaymentMethod(entity.getPaymentMethod());
        order.setDelivered(entity.isDelivered());
        order.setDeliveredDate(entity.getDeliveredDate());

        return order;
    }
}

