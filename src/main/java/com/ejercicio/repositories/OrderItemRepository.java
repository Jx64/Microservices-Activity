package com.ejercicio.repositories;

import com.ejercicio.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "SELECT oi FROM OrderItem oi WHERE oi.fkOrder.id=:idOrder")
    List<OrderItem> findByOrderId(@Param("idOrder")Long idOrder);

    @Query(value = "SELECT oi FROM OrderItem oi WHERE oi.fkProduct.id=:idProduct")
    List<OrderItem> findByProductId(@Param("idProduct") Long idProduct);

    @Query(value = "SELECT SUM(oi.amount * oi.unitPrice) FROM OrderItem oi WHERE oi.fkProduct.id =:idProduct")
    Double sumAllProductSales(@Param("idProduct") Long idProduct);
}
