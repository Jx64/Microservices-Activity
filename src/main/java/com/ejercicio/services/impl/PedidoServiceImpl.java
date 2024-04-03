package com.ejercicio.services.impl;

import com.ejercicio.dto.PedidoDto;
import com.ejercicio.dto.mapper.PedidoMapper;
import com.ejercicio.exceptions.CannotSaveException;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.PedidoRepository;
import com.ejercicio.services.PedidoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public List<PedidoDto> findAll() {
        List<Pedido> dataList = pedidoRepository.findAll();

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(pedidoMapper::pedidoToPedidoDto)
                .toList();
    }

    @Override
    public PedidoDto findById(Long id) {
        Pedido entityData = pedidoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        return pedidoMapper.pedidoToPedidoDto(entityData);
    }

    @Override
    public PedidoDto save(Pedido e) {
        if(e == null){
            throw new CannotSaveException("Invalid product");
        }
        return pedidoMapper.pedidoToPedidoDto(pedidoRepository.save(e));
    }

    @Override
    public PedidoDto update(Long id, Pedido e) {
        Pedido entityData = pedidoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        BeanUtils.copyProperties(e, entityData);
        pedidoRepository.save(entityData);

        return pedidoMapper.pedidoToPedidoDto(entityData);
    }

    @Override
    public void deleteById(Long id) {
        Pedido e = pedidoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        pedidoRepository.delete(e);
    }

    @Override
    public List<PedidoDto> findByFechaPedidoBetween(LocalDateTime start, LocalDateTime end) {
        List<Pedido> dataList = pedidoRepository.findByFechaPedidoBetween(start, end);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(pedidoMapper::pedidoToPedidoDto)
                .toList();
    }

    @Override
    public List<PedidoDto> findByClienteIdAndStatus(Long idCliente, PedidoStatus status) {
        List<Pedido> dataList = pedidoRepository.findByClienteIdAndStatus(idCliente, status);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(pedidoMapper::pedidoToPedidoDto)
                .toList();
    }

    @Override
    public List<PedidoDto> findByClienteWithItemPedido(Long idCliente) {
        List<Pedido> dataList = pedidoRepository.findByClienteWithItemPedido(idCliente);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(pedidoMapper::pedidoToPedidoDto)
                .toList();
    }
}
