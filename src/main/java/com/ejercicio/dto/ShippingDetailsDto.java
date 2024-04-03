package com.ejercicio.dto;

import lombok.Data;

@Data
public class ShippingDetailsDto {
    private Long id;
    private String address;
    private String shippingCompany;
    private Integer numberReference;
}
