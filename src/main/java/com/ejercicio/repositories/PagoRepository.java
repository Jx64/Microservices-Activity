package com.ejercicio.repositories;

import java.time.LocalDate;
import java.util.List;


public interface PagoRepository extends Repository<Pago> {
    List<Pago> findByFechaPagoBetween(LocalDate start, LocalDate end);
    List<Pago> findByPedidoIdAndMetodoPago(Long pedidoId, MetodoPago metodoPago);
}
