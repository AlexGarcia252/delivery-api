package com.example.delivery.infrastructure.rest.mapper;

import com.example.delivery.domain.model.Client;
import com.example.delivery.infrastructure.rest.dto.request.ClientRequestDto;
import com.example.delivery.infrastructure.rest.dto.response.ClientResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoMapper {
    public Client toDomain(ClientRequestDto dto) {
        return new Client(
                dto.getDocument(),
                dto.getNameAndSurname(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getShippingAddress()
        );
    }

    public ClientResponseDto toDto(Client domain) {
        return new ClientResponseDto(
                domain.getDocument(),
                domain.getNameAndSurname(),
                domain.getEmail(),
                domain.getPhoneNumber(),
                domain.getShippingAddress()
        );
    }
}
