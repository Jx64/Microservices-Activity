package com.ejercicio.dto;

import com.ejercicio.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class OrderDto {
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy",timezone = "GMT-5")
    private LocalDate orderDate;
    private OrderStatus status;
    private ClientDto fkClient;
}
