package com.example.delivery.infrastructure.rest.dto.response;

import com.example.delivery.domain.model.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private UUID uuid;

    // Fecha y hora de creación en formato ISO 8601
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationDateTime;


    private String clientDocument;

    private UUID productUuid;


    private Integer quantity;


    private String extraInformation;

    // Subtotal sin IVA (2 decimales)
    private BigDecimal subTotal;

    // Valor del IVA (19%)
    private BigDecimal tax;

    // Total incluyendo IVA (2 decimales)
    private BigDecimal grandTotal;


    private PaymentMethod paymentMethod;


    private boolean delivered;

    // Fecha y hora de entrega (inicialmente NULL)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deliveredDate;
}


