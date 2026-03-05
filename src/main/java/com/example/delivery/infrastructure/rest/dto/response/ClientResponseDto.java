package com.example.delivery.infrastructure.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {
    private String document;
    private String nameAndSurname;
    private String email;
    private String phoneNumber;
    private String shippingAddress;
}
