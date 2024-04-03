package com.ejercicio.repositories;

import com.ejercicio.entities.ShippingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingDetailsRepository extends JpaRepository<ShippingDetails, Long> {
    @Query("SELECT sp FROM ShippingDetails sp WHERE sp.fkOrder.id=:idOrder")
    List<ShippingDetails> findByOrderId(@Param("idOrder") Long idOrder);
    List<ShippingDetails> findByShippingCompany(String shippingCompany);

    @Query("SELECT sp FROM ShippingDetails sp " +
            "JOIN FETCH sp.fkOrder o " +
            "WHERE o.status = COALESCE(:#{T(com.ejercicio.entities.enums.OrderStatus).#status}, o.status)")
    List<ShippingDetails> findByOrderStatus(@Param("status") String status);
}
