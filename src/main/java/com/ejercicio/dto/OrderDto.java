package com.ejercicio.dto;

import com.ejercicio.entities.Client;
import com.ejercicio.entities.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private OrderStatus status;
    private Client fkClient;
}
