package com.example.delivery.infrastructure.database.h2.mapper;

import com.example.delivery.domain.model.Order;
import com.example.delivery.infrastructure.database.h2.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity entity);
}

