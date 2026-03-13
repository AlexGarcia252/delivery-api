package com.example.delivery.infrastructure.rest.mapper;

import com.example.delivery.domain.model.Order;
import com.example.delivery.infrastructure.rest.dto.request.OrderRequestDto;
import com.example.delivery.infrastructure.rest.dto.response.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderDtoMapper {
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "creationDateTime", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "subTotal", ignore = true)
    @Mapping(target = "tax", ignore = true)
    @Mapping(target = "grandTotal", ignore = true)
    @Mapping(target = "delivered", ignore = true)
    @Mapping(target = "deliveredDate", ignore = true)
    Order toDomain(OrderRequestDto dto);

    OrderResponseDto toDto(Order domain);
}

