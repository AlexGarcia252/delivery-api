package com.example.delivery.infrastructure.rest.mapper;

import com.example.delivery.domain.model.Order;
import com.example.delivery.infrastructure.rest.dto.request.OrderRequestDto;
import com.example.delivery.infrastructure.rest.dto.response.OrderResponseDto;
import org.springframework.stereotype.Component;


@Component
public class OrderDtoMapper {
    public Order toDomain(OrderRequestDto dto) {
        Order order = new Order();
        order.setClientDocument(dto.getClientDocument());
        order.setProductUuid(dto.getProductUuid());
        order.setQuantity(dto.getQuantity());
        order.setExtraInformation(dto.getExtraInformation());
        order.setPaymentMethod(dto.getPaymentMethod());
        return order;
    }

    public OrderResponseDto toDto(Order domain) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setUuid(domain.getUuid());
        dto.setCreationDateTime(domain.getCreationDateTime());
        dto.setClientDocument(domain.getClientDocument());
        dto.setProductUuid(domain.getProductUuid());
        dto.setQuantity(domain.getQuantity());
        dto.setExtraInformation(domain.getExtraInformation());
        dto.setSubTotal(domain.getSubTotal());
        dto.setTax(domain.getTax());
        dto.setGrandTotal(domain.getGrandTotal());
        dto.setPaymentMethod(domain.getPaymentMethod());
        dto.setDelivered(domain.isDelivered());
        dto.setDeliveredDate(domain.getDeliveredDate());
        return dto;
    }
}

