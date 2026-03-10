package com.example.delivery.infrastructure.rest.dto.request;

import com.example.delivery.domain.model.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotBlank(message = "El documento del cliente es obligatorio")
    @Size(max = 20, message = "El documento no puede exceder 20 caracteres")
    private String clientDocument;

    @NotNull(message = "El UUID del producto es obligatorio")
    private UUID productUuid;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Max(value = 99, message = "La cantidad máxima es 99")
    private Integer quantity;

    @Size(max = 511, message = "La información adicional no puede exceder 511 caracteres")
    private String extraInformation;

    @NotNull(message = "La forma de pago es obligatoria")
    private PaymentMethod paymentMethod;
}


