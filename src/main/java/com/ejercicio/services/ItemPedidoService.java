package com.ejercicio.services;

import com.ejercicio.dto.ItemPedidoDto;
import com.ejercicio.entities.ItemPedido;

import java.util.List;

public interface ItemPedidoService {
    List<ItemPedidoDto> findAll();
    ItemPedidoDto findById(Long id);
    List<ItemPedidoDto> findByProductoId(Long idProducto);
    Double sumaTotalVentasProducto(Long idProducto);
    List<ItemPedidoDto> findItemPedidoByPedido(Long idPedido);
    ItemPedidoDto save(ItemPedido itemPedido);
    ItemPedidoDto update(Long id, ItemPedido itemPedido);
    void deleteById(Long id);
}
