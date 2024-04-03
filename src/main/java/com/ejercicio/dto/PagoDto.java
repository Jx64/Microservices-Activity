package com.ejercicio.dto;

import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.enums.MetodoPago;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoDto {
    private Long id;
    private Double totalPago;
    private LocalDate fechaPago;
    private MetodoPago metodoPago;
    private Pedido pedido;
}
