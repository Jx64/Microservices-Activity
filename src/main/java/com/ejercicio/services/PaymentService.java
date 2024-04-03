package com.ejercicio.services;

import com.ejercicio.dto.PaymentDto;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    List<PaymentDto> findAll();
    PaymentDto findById(Long id);
    PaymentDto save(PaymentDto entityDto);
    PaymentDto update(Long id, PaymentDto entityDto);
    void deleteById(Long id);
    List<PaymentDto> searchByFecha(LocalDate start, LocalDate end);
    List<PaymentDto> searchByPedidoAndPaymentMethod(Long idOrder, String paymentMethod);
}
