package com.example.delivery.infrastructure.rest.dto.response;

public record ErrorResponseDto(
        String code,
        String timestamp,
        String description,
        String exception
) {
}

