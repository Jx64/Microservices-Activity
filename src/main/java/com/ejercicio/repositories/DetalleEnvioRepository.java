package com.ejercicio.repositories;

import com.ejercicio.entities.DetalleEnvio;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.Producto;
import com.ejercicio.entities.enums.PedidoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DetalleEnvioRepository extends Repository<DetalleEnvio> {
    List<DetalleEnvio> findByPedidoId(Pedido pedido);
    List<DetalleEnvio> findByTransportadora(String transportadora);

    @Query("SELECT de FROM DetalleEnvio de JOIN FETCH de.pedidoId p WHERE p.status = :estado")
    List<DetalleEnvio> findByEstado(PedidoStatus estado);
}
