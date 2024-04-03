package com.ejercicio.repositories;

import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DetalleEnvioRepository extends Repository<DetalleEnvio> {
    List<DetalleEnvio> findByPedidoId(Pedido pedido);
    List<DetalleEnvio> findByTransportadora(String transportadora);

    @Query("SELECT de FROM DetalleEnvio de JOIN FETCH de.pedidoId p WHERE p.status = :estado")
    List<DetalleEnvio> findByEstado(PedidoStatus estado);
}
