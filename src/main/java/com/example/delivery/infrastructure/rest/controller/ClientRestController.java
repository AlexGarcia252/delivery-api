package com.example.delivery.infrastructure.rest.controller;

import com.example.delivery.application.service.ClientInteractorService;
import com.example.delivery.domain.model.Client;
import com.example.delivery.infrastructure.rest.dto.request.ClientRequestDto;
import com.example.delivery.infrastructure.rest.dto.response.ClientResponseDto;
import com.example.delivery.infrastructure.rest.mapper.ClientDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Clientes", description = "Gestión de clientes en el sistema de entregas")
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientInteractorService clientService;
    private final ClientDtoMapper clientDtoMapper;

    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente en el sistema. El documento debe ser único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un cliente con ese documento")
    })
    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody @Valid ClientRequestDto dto) {
        Client domain = clientDtoMapper.toDomain(dto);
        Client saved = clientService.createClient(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDtoMapper.toDto(saved));
    }

    @Operation(summary = "Obtener cliente por documento", description = "Recupera la información de un cliente específico usando su documento de identidad.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{document}")
    public ResponseEntity<ClientResponseDto> getClientByDocument(@PathVariable String document) {
        Client client = clientService.execute(document);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clientDtoMapper.toDto(client));
    }


    @Operation(summary = "Actualizar cliente por documento", description = "Actualiza la información de un cliente específico usando su documento de identidad.")
    @PutMapping ("/{document}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
    })
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable String document ,@Valid @RequestBody ClientRequestDto dto ){
        Client domain = clientDtoMapper.toDomain(dto);
        Client updateClient = clientService.updateClient(document,domain);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientDtoMapper.toDto(updateClient));
    }

    @Operation(summary = "Borrar al cliente", description = "Borra al cliente por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente Eliminado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{document}/delete")
    public ResponseEntity<Void> deleteClient(@PathVariable String document){
        clientService.deleteClient(document);
        return ResponseEntity.noContent().build();
    }

}
