package com.ejercicio.dto;

import com.ejercicio.entities.Order;
import lombok.Data;

@Data
public class ShippingDetailsDto {
    private Long id;
    private String address;
    private String shippingCompany;
    private Integer numberReference;
    private Order fkOrder;
}
