package com.ejercicio.services;


import com.ejercicio.dto.DetalleEnvioDto;
import com.ejercicio.entities.DetalleEnvio;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.enums.PedidoStatus;

import java.util.List;

public interface DetalleEnvioService {
    List<DetalleEnvioDto> findAll();
    DetalleEnvioDto findDetalleEnvioById(Long id);
    List<DetalleEnvioDto> findDetalleEnvioByPedido(Pedido idPedido);
    List<DetalleEnvioDto> findDetalleEnvioByTransportadora(String name);
    List<DetalleEnvioDto> findDetalleEnvioByEstado(PedidoStatus pedidoStatus);
    DetalleEnvioDto save(DetalleEnvio detalleEnvio);
    DetalleEnvioDto update(Long id, DetalleEnvio detalleEnvio);
    void deleteById(Long id);
}