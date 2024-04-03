package com.ejercicio.repositories;

import com.ejercicio.entities.Pago;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.Producto;
import com.ejercicio.entities.enums.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface PagoRepository extends Repository<Pago> {
    List<Pago> findByFechaPagoBetween(LocalDate start, LocalDate end);
    List<Pago> findByPedidoIdAndMetodoPago(Long pedidoId, MetodoPago metodoPago);
}
