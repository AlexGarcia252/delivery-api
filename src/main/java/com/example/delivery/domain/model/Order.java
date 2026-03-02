package com.example.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private UUID uuid;
    private LocalDateTime creationDateTime;
    private String clientDocument;
    private UUID productUuid;
    private Integer quantity;
    private String extraInformation;
    private BigDecimal price;
    private BigDecimal subTotal;
    private BigDecimal tax;
    private BigDecimal grandTotal;
    private PaymentMethod paymentMethod;
    private boolean delivered;
    private LocalDateTime deliveredDate;

    public void validateAndFormat() {
        // Generar UUID si no existe
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }

        // Generar fecha y hora del pedido en el servidor (GMT-5)
        if (this.creationDateTime == null) {
            this.creationDateTime = LocalDateTime.now(ZoneId.of("America/Bogota"));
        }

        // Validaciones de datos de entrada
        if (this.clientDocument == null || this.clientDocument.isBlank()) {
            throw new IllegalArgumentException("El documento del cliente es obligatorio");
        }

        if (this.productUuid == null) {
            throw new IllegalArgumentException("El UUID del producto es obligatorio");
        }

        if (this.quantity == null || this.quantity < 1 || this.quantity >= 100) {
            throw new IllegalArgumentException("La cantidad debe ser un número entero entre 1 y 99");
        }

        if (this.extraInformation == null) {
            this.extraInformation = "";
        }

        if (this.extraInformation.length() > 511) {
            throw new IllegalArgumentException("La información adicional no puede superar los 511 caracteres");
        }

        if (this.paymentMethod == null) {
            throw new IllegalArgumentException("La forma de pago es obligatoria");
        }

        if (this.price == null || this.price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a cero");
        }

        // Calcular campos derivados (subTotal, IVA, total)
        calculateTotals();

        // Inicializar campos de entrega
        if (!this.delivered) {
            this.deliveredDate = null;
        }
    }

    // Método privado para calcular totales
    private void calculateTotals() {
        // Subtotal = precio unitario * cantidad
        this.subTotal = this.price.multiply(BigDecimal.valueOf(this.quantity));

        // IVA = subtotal * 0.19
        BigDecimal ivaTaxRate = BigDecimal.valueOf(0.19);
        this.tax = this.subTotal.multiply(ivaTaxRate)
                .setScale(2, java.math.RoundingMode.HALF_UP);

        // Total = subtotal + IVA
        this.grandTotal = this.subTotal.add(this.tax)
                .setScale(2, java.math.RoundingMode.HALF_UP);
    }
}

