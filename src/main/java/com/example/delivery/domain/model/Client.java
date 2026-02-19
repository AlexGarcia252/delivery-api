package com.example.delivery.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    //@NotBlank(message = "El número de documento es obligatorio")
    //@Size(max = 20, message = "El número de documento no puede exceder 20 caracteres")
    //@Pattern(regexp = "^(CC|CE|P)[0-9\\-]+$", message = "Formato inválido. Debe incluir tipo (CC, CE, P..) seguido del guion y numero")
    private String document;

    private String nameAndSurname;
    @Email
    private String email;
    private String phoneNumber;
    private String shippingAddress;
}


