package com.ejercicio.repositories;

import com.ejercicio.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPaymentDateBetween(LocalDate start, LocalDate end);

    @Query(value = "SELECT p FROM Payment p " +
            "WHERE p.fkOrder.id=:idOrder " +
            "AND p.paymentMethod = COALESCE(:#{T(com.ejercicio.entities.enums.PaymentMethod).#paymentMethod}, p.paymentMethod)")
    List<Payment> findByOrderIdAndPaymentMethod(@Param("idOrder") Long idOrder, @Param("paymentMethod") String paymentMethod);
}
