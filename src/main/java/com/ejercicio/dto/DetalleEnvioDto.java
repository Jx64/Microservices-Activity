package com.ejercicio.dto;

import com.ejercicio.entities.Pedido;
import lombok.Data;

@Data
public class DetalleEnvioDto {
    private Long id;

    private String direccion;

    private String transportadora;

    private Integer numeroGuia;
    private Pedido pedido;
}
