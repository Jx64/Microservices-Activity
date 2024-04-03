package com.ejercicio.mapper;

import com.ejercicio.dto.OrderItemDto;
import com.ejercicio.entities.OrderItem;
import org.mapstruct.Mapper;

@Mapper
public interface OrderItemMapper {
    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);
    OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto);
}
