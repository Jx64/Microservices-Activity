package com.ejercicio.services;

import com.ejercicio.dto.PedidoDto;
import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.enums.MetodoPago;
import com.ejercicio.entities.enums.PedidoStatus;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
    List<PedidoDto> findAll();
    PedidoDto findById(Long id);
    PedidoDto save(Pedido e);
    PedidoDto update(Long id, Pedido e);
    void deleteById(Long id);
    List<PedidoDto> findByFechaPedidoBetween(LocalDateTime start, LocalDateTime end);
    List<PedidoDto> findByClienteIdAndStatus(Long idCliente, PedidoStatus status);

    List<PedidoDto> findByClienteWithItemPedido(Long idCliente);
}
