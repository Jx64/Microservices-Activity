package com.ejercicio.dto.mapper;

import com.ejercicio.dto.ItemPedidoDto;
import com.ejercicio.entities.ItemPedido;
import org.mapstruct.Mapper;

@Mapper
public interface ItemPedidoMapper {
    ItemPedidoDto itemPedidoToItemPedidoDto(ItemPedido itemPedido);
    ItemPedido itemPedidoDtoToItemPedido(ItemPedidoDto itemPedidoDto);
}
