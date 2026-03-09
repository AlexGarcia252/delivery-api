package com.example.delivery.infrastructure.rest.controller;

import com.example.delivery.application.ICreateOrderInteractor;
import com.example.delivery.application.IDeleteOrderInteractor;
import com.example.delivery.domain.model.Order;
import com.example.delivery.infrastructure.rest.mapper.OrderDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Órdenes", description = "Gestión de órdenes de entrega en el sistema")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final ICreateOrderInteractor createOrderInteractor;
    private final OrderDtoMapper orderDtoMapper;
    private final IDeleteOrderInteractor deleteOrderInteractor;

    @Operation(summary = "Crear nueva orden", description = "Crea una nueva orden de entrega. Se valida que el cliente y producto existan, y se calculan automáticamente los totales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la orden inválidos o incompletos"),
            @ApiResponse(responseCode = "404", description = "Cliente o producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto dto) {

        Order order = orderDtoMapper.toDomain(dto);

        Order createdOrder = createOrderInteractor.createOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderDtoMapper.toDto(createdOrder));
    }

    @DeleteMapping("/{uuid}")
    @Operation(
            summary = "Eliminar un pedido",
            description = "Elimina permanentemente un pedido del sistema usando su identificador único (UUID)." //
    )
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID uuid) {
        deleteOrderInteractor.deleteOrder(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

