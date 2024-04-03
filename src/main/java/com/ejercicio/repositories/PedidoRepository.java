package com.ejercicio.repositories;

import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.Producto;
import com.ejercicio.entities.enums.PedidoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends Repository<Pedido> {

    List<Pedido> findByFechaPedidoBetween(LocalDateTime start, LocalDateTime end);
    List<Pedido> findByClienteIdAndStatus(Long cliente, PedidoStatus status);

    @Query(value = "SELECT p FROM Pedido p JOIN FETCH p.itemPedidos WHERE p.clienteId = :clienteId")
    List<Pedido> findByClienteWithItemPedido(@Param("clienteId") Long cliente);
}
