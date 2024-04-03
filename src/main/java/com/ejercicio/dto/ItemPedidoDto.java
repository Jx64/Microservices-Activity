package com.ejercicio.dto;

import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.Producto;
import lombok.Data;

@Data
public class ItemPedidoDto {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Pedido pedido;
    private Producto producto;

}
