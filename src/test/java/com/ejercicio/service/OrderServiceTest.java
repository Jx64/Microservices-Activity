package com.ejercicio.service;

import com.ejercicio.dto.OrderDto;
import com.ejercicio.entities.Order;
import com.ejercicio.entities.enums.OrderStatus;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.OrderMapper;
import com.ejercicio.repositories.OrderRepository;
import com.ejercicio.services.impl.OrderServiceImpl;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    Order order;

    @BeforeEach
    void setUp(){
        LocalDate date = LocalDate.of(2024,1,23);

        order = Order.builder()
                .id(1L)
                .orderDate(date)
                .status(OrderStatus.ENTREGADO)
                .build();
    }

    @Test
    void OrderService_FindAll_ShouldGetAllOrders() {
        List<Order> ordersList = new ArrayList<>();
        ordersList.add(order);

        given(orderRepository.findAll())
                .willReturn(ordersList);

        List<OrderDto> orderDtoList = orderService.findAll();

        assertNotNull(orderDtoList);
    }

    @Test
    void givenOrder_whenFindById_thenReturnOrder(){
        Long idOrder = 1L;

        given(orderRepository.findById(idOrder))
                .willReturn(Optional.of(order));

        OrderDto orderDto = new OrderDto();

        given(orderMapper.orderToOrderDto(any(Order.class)))
                .willReturn(orderDto);

        OrderDto result = orderService.findById(idOrder);

        assertNotNull(result);
    }

    @Test
    void givenOrder_whenIdNotFound_thenReturnException() {
        given(orderRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            orderService.findById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenOrder_whenUpdate_thenReturnOrderUpdated() {
        LocalDate new_date = LocalDate.of(2024,2,23);

        OrderDto orderUpdate = new OrderDto();
        orderUpdate.setOrderDate(new_date);
        orderUpdate.setStatus(OrderStatus.ENTREGADO);

        Long idOrder = 1L;

        given(orderRepository.findById(idOrder))
                .willReturn(Optional.of(order));

        assertDoesNotThrow(() -> {
            orderService.update(idOrder, orderUpdate);
        });
    }

    @Test
    void givenAnId_whenOrderExist_thenDeleteOrder() {
        Long idOrder = 1L;

        given(orderRepository.findById(idOrder))
                .willReturn(Optional.of(order));

        willDoNothing().given(orderRepository).delete(any());

        orderService.deleteById(idOrder);

        verify(orderRepository, times(1)).delete(any());
    }

}
