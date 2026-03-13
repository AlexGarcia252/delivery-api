package com.example.delivery.domain.model;

import com.example.delivery.domain.exception.client.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "^(CC|CE|P)-\\d+$", message = "El documento debe tener formato CC-<numeros>, CE-<numeros> o P-<numeros>")
    @Size(max = 20, message = "El documento no puede superar 20 caracteres")
    private String document;

    @NotBlank(message = "El nombre y apellido es obligatorio")
    @Size(max = 255, message = "El nombre y apellido no puede superar 255 caracteres")
    private String nameAndSurname;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    @Size(max = 255, message = "El email no puede superar 255 caracteres")
    private String email;

    @NotBlank(message = "El numero de celular es obligatorio")
    @Pattern(regexp = "^\\d{1,10}$", message = "El numero de celular debe tener solo digitos y maximo 10")
    private String phoneNumber;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 500, message = "La direccion no puede superar 500 caracteres")
    private String shippingAddress;

    public void validateAndFormat() {
        if (document == null || !document.matches("^(CC|CE|P)-\\d+$") || document.length() > 20) {
            throw new InvalidClientDocumentException("El documento debe tener formato CC|CE|P-<numeros> y maximo 20 caracteres");
        }
        if (nameAndSurname == null || nameAndSurname.isBlank() || nameAndSurname.length() > 255) {
            throw new ClientNameLengthException("El nombre y apellido es obligatorio y no puede superar 255 caracteres");
        }
        if (email == null || email.isBlank() || email.length() > 255) {
            throw new ClientEmailLengthException("El email es obligatorio y no puede superar 255 caracteres");
        }
        if (phoneNumber == null || !phoneNumber.matches("^\\d{1,10}$")) {
            throw new ClientPhoneNumberFormatException("El numero de celular debe tener solo digitos y maximo 10 caracteres");
        }
        if (shippingAddress == null || shippingAddress.isBlank() || shippingAddress.length() > 500) {
            throw new ClientShippingAddressLengthException("La direccion es obligatoria y no puede superar 500 caracteres");
        }
    }
}


