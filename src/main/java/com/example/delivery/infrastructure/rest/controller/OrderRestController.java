package com.example.delivery.infrastructure.rest.controller;

import com.example.delivery.domain.port.in.IOrden.ICreateOrderInteractor;
import com.example.delivery.domain.port.in.IOrden.IDeleteOrderInteractor;
import com.example.delivery.domain.port.in.IOrden.IDeliverOrderInteractor;
import com.example.delivery.domain.model.Order;
import com.example.delivery.infrastructure.rest.dto.request.OrderRequestDto;
import com.example.delivery.infrastructure.rest.dto.response.OrderResponseDto;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Tag(name = "Órdenes", description = "Gestión de órdenes de entrega en el sistema")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final ICreateOrderInteractor createOrderInteractor;
    private final IDeleteOrderInteractor deleteOrderInteractor;
    private final IDeliverOrderInteractor deliverOrderInteractor;
    private final OrderDtoMapper orderDtoMapper;

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
            description = "Elimina permanentemente un pedido del sistema usando su identificador único (UUID)."
    )
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID uuid) {
        deleteOrderInteractor.deleteOrder(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{uuid}/delivered/{timestamp}")
    @Operation(
            summary = "Marcar pedido como entregado",
            description = "Actualiza el estado de un pedido a entregado, registrando la fecha y hora de entrega. " +
                    "El timestamp debe estar en formato ISO 8601. La zona horaria asumida es siempre America/Bogotá (GMT-5). " +
                    "Formatos aceptados: sin zona '2026-03-12T15:30:00' o con offset '2026-03-12T15:30:00-05:00'."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Formato de UUID o timestamp inválido"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<OrderResponseDto> deliverOrder(
            @PathVariable UUID uuid,
            @PathVariable String timestamp) {

        final ZoneId BOGOTA = ZoneId.of("America/Bogota");
        LocalDateTime deliveredDate;
        try {
            // Intentar primero sin zona horaria (formato más común en este sistema)
            try {
                deliveredDate = LocalDateTime.parse(timestamp);
            } catch (DateTimeParseException e) {
                // Si trae offset o zona, convertir a America/Bogota
                deliveredDate = ZonedDateTime.parse(timestamp)
                        .withZoneSameInstant(BOGOTA)
                        .toLocalDateTime();
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Formato de timestamp inválido: '" + timestamp + "'. " +
                            "Use ISO 8601 en zona America/Bogota (GMT-5). " +
                            "Ejemplos: '2026-03-12T15:30:00' o '2026-03-12T15:30:00-05:00'");
        }

        Order updatedOrder = deliverOrderInteractor.deliverOrder(uuid, deliveredDate);
        return ResponseEntity.ok(orderDtoMapper.toDto(updatedOrder));
    }
}

