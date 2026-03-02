package com.example.delivery.infrastructure.database.h2.entity;

import com.example.delivery.domain.model.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId; // ID autoincremental privado, nunca se publica

    // UUID único para acceder a la orden vía GET
    @Column(unique = true, nullable = false)
    private UUID uuid;

    // Fecha y hora de creación del pedido
    @NotNull(message = "La fecha y hora de creación es obligatoria")
    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    // Documento del cliente (FK lógica, se valida en negocio)
    @NotBlank(message = "El documento del cliente es obligatorio")
    @Size(max = 20, message = "El documento no puede exceder 20 caracteres")
    @Column(nullable = false)
    private String clientDocument;

    // UUID del producto (FK lógica, se valida en negocio)
    @NotNull(message = "El UUID del producto es obligatorio")
    @Column(nullable = false)
    private UUID productUuid;

    // Cantidad de unidades del producto
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Max(value = 99, message = "La cantidad máxima es 99")
    @Column(nullable = false)
    private Integer quantity;

    // Información adicional del pedido
    @Size(max = 511, message = "La información adicional no puede exceder 511 caracteres")
    @Column(length = 511)
    private String extraInformation;

    // Precio unitario del producto al momento de la orden
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a cero")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 2 decimales")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Subtotal sin IVA
    @NotNull(message = "El subtotal es obligatorio")
    @Digits(integer = 10, fraction = 2, message = "El subtotal debe tener máximo 2 decimales")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;

    // Valor del IVA (19%)
    @NotNull(message = "El IVA es obligatorio")
    @Digits(integer = 10, fraction = 2, message = "El IVA debe tener máximo 2 decimales")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;

    // Total incluyendo IVA
    @NotNull(message = "El total es obligatorio")
    @Digits(integer = 10, fraction = 2, message = "El total debe tener máximo 2 decimales")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal grandTotal;

    // Forma de pago
    @NotNull(message = "La forma de pago es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    // Flag indicador de entrega
    @Column(nullable = false)
    private boolean delivered = false;

    // Fecha y hora de entrega (inicialmente NULL)
    @Column
    private LocalDateTime deliveredDate;

    // Soft delete
    private boolean deleted = false;
}


