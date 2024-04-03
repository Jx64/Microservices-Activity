package com.ejercicio.dto.mapper;

import com.ejercicio.dto.PedidoDto;
import com.ejercicio.entities.Pedido;
import org.mapstruct.Mapper;

@Mapper
public interface PedidoMapper {
    PedidoDto pedidoToPedidoDto(Pedido pedido);
    Pedido pedidoDtoToPedido(PedidoDto pedidoDto);
}
