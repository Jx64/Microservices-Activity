package com.ejercicio.repositories;

import com.ejercicio.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderDateBetween(LocalDate start, LocalDate end);

    @Query(value = "SELECT o FROM Order o " +
            "WHERE o.fkClient.id=:idClient " +
            "AND o.status = COALESCE(:#{T(com.ejercicio.entities.enums.OrderStatus).#status}, o.status)")
    List<Order> findByClientWithStatus(@Param("idClient") Long idClient, @Param("status") String status);

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.fkClient.id = :idClient")
    List<Order> findByClientWithItemPedido(@Param("idClient") Long idClient);
}
