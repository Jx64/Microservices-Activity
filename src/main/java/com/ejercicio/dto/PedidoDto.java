package com.ejercicio.dto;

import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.enums.PedidoStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PedidoDto {
    private Long id;
    private LocalDateTime fechaPedido;
    private PedidoStatus status;
    private Cliente cliente;
}
