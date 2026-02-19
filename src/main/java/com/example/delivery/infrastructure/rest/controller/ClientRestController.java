package com.example.delivery.infrastructure.rest.controller;

import com.example.delivery.application.ICreateClientInteractor;
import com.example.delivery.domain.model.Client;
import com.example.delivery.infrastructure.rest.mapper.ClientDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final ICreateClientInteractor createClientInteractor;
    private final ClientDtoMapper clientDtoMapper;

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody @Valid ClientRequestDto dto) {
        Client domain = clientDtoMapper.toDomain(dto);
        Client saved = createClientInteractor.createClient(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDtoMapper.toDto(saved));
    }

}
