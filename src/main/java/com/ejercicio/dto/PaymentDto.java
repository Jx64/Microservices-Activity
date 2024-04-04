package com.ejercicio.dto;

import com.ejercicio.entities.Order;
import com.ejercicio.entities.enums.PaymentMethod;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDto {
    private Long id;
    private double paymentTotal;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;
    private Order fkOrder;
}
