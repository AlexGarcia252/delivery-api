package com.example.delivery.infrastructure.rest.controller;

import com.example.delivery.application.ICreateClientInteractor;
import com.example.delivery.application.IDeleteClientInteractor;
import com.example.delivery.application.IGetClientByIdInteractor;
import com.example.delivery.domain.model.Client;
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

    private final ICreateClientInteractor createClientInteractor;
    private final IGetClientByIdInteractor getClientByIdInteractor;
    private final IDeleteClientInteractor deleteClientInteractor;
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
        Client saved = createClientInteractor.createClient(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDtoMapper.toDto(saved));
    }

    @Operation(summary = "Obtener cliente por documento", description = "Recupera la información de un cliente específico usando su documento de identidad.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{document}")
    public ResponseEntity<ClientResponseDto> getClientByDocument(@PathVariable String document) {
        Client client = getClientByIdInteractor.execute(document);

        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clientDtoMapper.toDto(client));
    }

    @Operation(summary = "Borrar al cliente", description = "Borra al cliente por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{document}/delete")
    public ResponseEntity<ClientResponseDto> deleteClient(@PathVariable String document){
        Client client = getClientByIdInteractor.execute(document);

        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        deleteClientInteractor.deleteClient(client);
        return ResponseEntity.ok(null);
    }

}
