package com.ejercicio.services;

import com.ejercicio.dto.PagoDto;
import com.ejercicio.entities.Pago;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.enums.MetodoPago;

import java.time.LocalDate;
import java.util.List;

public interface PagoService {
    List<PagoDto> findAll();
    PagoDto findPagoById(Long id);
    List<PagoDto> findByPedidoAndMetodoPago(Long idPedido, MetodoPago metodoPago);
    List<PagoDto> findPagoByStarFechaAndFinalFecha(LocalDate starDate, LocalDate endDate);
    PagoDto save(Pago pago);
    PagoDto update(Long id, Pago pago);
    void deleteById(Long id);
}
