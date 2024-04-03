package com.ejercicio.service;


import com.ejercicio.dto.OrderItemDto;
import com.ejercicio.entities.OrderItem;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.OrderItemMapper;
import com.ejercicio.repositories.OrderItemRepository;
import com.ejercicio.services.impl.OrderItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderItemRepositoryTest {
    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    OrderItem orderItem;

    @BeforeEach
    void setUp(){
        LocalDate date = LocalDate.of(2024,1,23);

        orderItem = OrderItem.builder()
                .id(1L)
                .amount(5)
                .unitPrice(23.0)
                .build();
    }

    @Test
    void OrderItemService_FindAll_ShouldGetAllOrderItems() {
        List<OrderItem> ordersItemList = new ArrayList<>();
        ordersItemList.add(orderItem);

        given(orderItemRepository.findAll())
                .willReturn(ordersItemList);

        List<OrderItemDto> orderDtoList = orderItemService.findAll();

        assertNotNull(orderDtoList);
    }

    @Test
    void givenOrderItem_whenFindById_thenReturnOrderItem(){
        Long idOrder = 1L;

        given(orderItemRepository.findById(idOrder))
                .willReturn(Optional.of(orderItem));

        OrderItemDto orderItemDto = new OrderItemDto();

        given(orderItemMapper.orderItemToOrderItemDto(any(OrderItem.class)))
                .willReturn(orderItemDto);

        OrderItemDto result = orderItemService.findById(idOrder);

        assertNotNull(result);
    }

    @Test
    void givenOrderItem_whenIdNotFound_thenReturnException() {
        given(orderItemRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            orderItemService.findById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenOrderItem_whenUpdate_thenReturnOrderItemUpdated() {
        OrderItemDto orderUpdate = new OrderItemDto();
        orderUpdate.setAmount(2);
        orderUpdate.setUnitPrice(23.0);

        Long idOrderItem = 1L;

        given(orderItemRepository.findById(idOrderItem))
                .willReturn(Optional.of(orderItem));

        assertDoesNotThrow(() -> {
            orderItemService.update(idOrderItem, orderUpdate);
        });
    }

    @Test
    void givenAnId_whenPedidoExist_thenDeleteOrderItem() {
        Long idOrder = 1L;

        given(orderItemRepository.findById(idOrder))
                .willReturn(Optional.of(orderItem));

        willDoNothing().given(orderItemRepository).delete(any());

        orderItemService.deleteById(idOrder);

        verify(orderItemRepository, times(1)).delete(any());
    }
}
