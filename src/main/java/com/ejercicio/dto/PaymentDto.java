package com.ejercicio.dto;

import com.ejercicio.entities.Order;
import com.ejercicio.entities.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PaymentDto {
    private Long id;
    private double paymentTotal;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy",timezone = "GMT-5")
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;
    private OrderDto fkOrder;
}
