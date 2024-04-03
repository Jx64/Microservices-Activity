package com.ejercicio.repositories;

import com.ejercicio.entities.ItemPedido;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemPedidoRepository extends Repository<ItemPedido> {
    List<ItemPedido> findByPedidoId(Long pedidoId);
    List<ItemPedido> findByProductoId(Long productoId);

    @Query(value = "SELECT SUM(i.cantidad * i.precioUnitario) FROM ItemPedido i WHERE i.productoId =:productoId")
    Double sumaTotalVentasProducto(@Param("productoId") Long producto);
}
