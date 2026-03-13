package com.example.delivery.infrastructure.rest.mapper;

import com.example.delivery.domain.model.Client;
import com.example.delivery.infrastructure.rest.dto.request.ClientRequestDto;
import com.example.delivery.infrastructure.rest.dto.response.ClientResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientDtoMapper {
    Client toDomain(ClientRequestDto dto);

    ClientResponseDto toDto(Client domain);
}
