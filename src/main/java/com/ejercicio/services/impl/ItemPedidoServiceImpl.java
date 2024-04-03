package com.ejercicio.services.impl;

import com.ejercicio.dto.ItemPedidoDto;
import com.ejercicio.dto.mapper.ItemPedidoMapper;
import com.ejercicio.exceptions.CannotSaveException;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.ItemPedidoRepository;
import com.ejercicio.services.ItemPedidoService;

import java.util.List;

public class ItemPedidoServiceImpl implements ItemPedidoService {
    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemPedidoMapper itemPedidoMapper;

    public ItemPedidoServiceImpl(ItemPedidoRepository itemPedidoRepository, ItemPedidoMapper itemPedidoMapper){
        this.itemPedidoMapper = itemPedidoMapper;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public List<ItemPedidoDto> findAll() {
        List<ItemPedido> items = itemPedidoRepository.findAll();

        if (items.isEmpty()){
            throw new DataNotFoundException("ItemPedido wasn´t found ");
        }

        return items.stream().map(itemPedidoMapper::itemPedidoToItemPedidoDto).toList();
    }

    @Override
    public ItemPedidoDto findById(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Item wasn't found"));
        return itemPedidoMapper.itemPedidoToItemPedidoDto(itemPedido);
    }

    @Override
    public List<ItemPedidoDto> findByProductoId(Long idProducto) {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findByProductoId(idProducto);

        if(itemPedidos.isEmpty()){
            throw new DataNotFoundException("ItemPedidos wasn't found");
        }

        return itemPedidos.stream().map(itemPedidoMapper::itemPedidoToItemPedidoDto).toList();
    }

    @Override
    public Double sumaTotalVentasProducto(Long idProducto) {
        Double sumaVentas = itemPedidoRepository.sumaTotalVentasProducto(idProducto);
        return sumaVentas;
    }

    @Override
    public List<ItemPedidoDto> findItemPedidoByPedido(Long idPedido) {
        List<ItemPedido> items = itemPedidoRepository.findByPedidoId(idPedido);

        if (items.isEmpty()){
            throw new DataNotFoundException("Items wasn't found");
        }
        return items.stream().map(itemPedidoMapper::itemPedidoToItemPedidoDto).toList();
    }

    @Override
    public ItemPedidoDto save(ItemPedido itemPedido) {
        if(itemPedido == null){
            throw new CannotSaveException("Invalid itemPedido");
        }
        return itemPedidoMapper.itemPedidoToItemPedidoDto(itemPedidoRepository.save(itemPedido));
    }

    @Override
    public ItemPedidoDto update(Long id, ItemPedido itemPedido) {
        return itemPedidoRepository.findById(id).map(itemDB -> {
            itemDB.setPedidoId(itemPedido.getPedidoId());
            itemDB.setCantidad(itemPedido.getCantidad());
            itemDB.setPedidoId(itemPedido.getPedidoId());
            itemDB.setPrecioUnitario(itemPedido.getPrecioUnitario());

            return itemPedidoMapper.itemPedidoToItemPedidoDto(itemPedidoRepository.save(itemDB));
        }).orElseThrow(()-> new DataNotFoundException("Items wasn't found"));
    }

    @Override
    public void deleteById(Long id) {
        ItemPedido item = itemPedidoRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Items wasn't found"));
        itemPedidoRepository.delete(item);
    }
}
