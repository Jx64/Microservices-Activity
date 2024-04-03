package com.ejercicio.services;

import com.ejercicio.dto.OrderDto;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    OrderDto findById(Long id);
    OrderDto save(OrderDto entityDto);
    OrderDto update(Long id, OrderDto entityDto);
    void deleteById(Long id);
    List<OrderDto> searchByFecha(LocalDate start, LocalDate end);
    List<OrderDto> searchByClienteAndStatus(Long idCliente, String status);
    List<OrderDto> searchClientOrderItems(@Param("idClient") Long idClient);
}
