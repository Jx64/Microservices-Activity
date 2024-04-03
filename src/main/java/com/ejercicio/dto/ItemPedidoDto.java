package com.ejercicio.dto;

import lombok.Data;

@Data
public class ItemPedidoDto {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Pedido pedido;
    private Producto producto;

}
