package com.ejercicio.services;

import com.ejercicio.dto.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> findAll();
    OrderItemDto findById(Long id);
    OrderItemDto save(OrderItemDto entityDto);
    OrderItemDto update(Long id, OrderItemDto entityDto);
    void deleteById(Long id);
    List<OrderItemDto> findByOrderId(Long idOrder);

    List<OrderItemDto> findByProductId(Long idProduct);

    Double sumAllProductSales(Long idProduct);
}
