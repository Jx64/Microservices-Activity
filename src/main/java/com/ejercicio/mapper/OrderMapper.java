package com.ejercicio.mapper;

import com.ejercicio.dto.OrderDto;
import com.ejercicio.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);
    Order orderDtoToOrder(OrderDto orderDto);
}
