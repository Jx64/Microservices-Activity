package com.ejercicio.services.impl;

import com.ejercicio.dto.OrderDto;
import com.ejercicio.entities.Order;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.OrderMapper;
import com.ejercicio.repositories.OrderRepository;
import com.ejercicio.services.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDto> findAll() {
        List<Order> dataList = orderRepository.findAll();
        return dataList.stream().map(orderMapper::orderToOrderDto).toList();
    }

    @Override
    public OrderDto findById(Long id) {
        Order data = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        return orderMapper.orderToOrderDto(data);
    }

    @Override
    public OrderDto save(OrderDto entityDto) {
        Order entity = orderMapper.orderDtoToOrder(entityDto);
        return orderMapper.orderToOrderDto(orderRepository.save(entity));
    }

    @Override
    public OrderDto update(Long id, OrderDto entityDto) {
        Order entity = orderMapper.orderDtoToOrder(entityDto);
        Order data = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));
        data.updateWith(entity);
        orderRepository.save(data);
        return orderMapper.orderToOrderDto(data);
    }

    @Override
    public void deleteById(Long id) {
        Order entity = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        orderRepository.delete(entity);
    }

    @Override
    public List<OrderDto> searchByFecha(LocalDate start, LocalDate end) {
        List<Order> dataList = orderRepository.findByOrderDateBetween(start, end);
        return dataList.stream().map(orderMapper::orderToOrderDto).toList();
    }

    @Override
    public List<OrderDto> searchByClienteAndStatus(Long idCliente, String status) {
        List<Order> dataList = orderRepository.findByClientWithStatus(idCliente, status);
        return dataList.stream().map(orderMapper::orderToOrderDto).toList();
    }

    @Override
    public List<OrderDto> searchClientOrderItems(Long idClient) {
        List<Order> orderList = orderRepository.findByClientWithItemPedido(idClient);
        return orderList.stream().map(orderMapper::orderToOrderDto).toList();
    }
}
