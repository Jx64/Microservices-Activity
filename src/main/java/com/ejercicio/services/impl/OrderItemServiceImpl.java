package com.ejercicio.services.impl;

import com.ejercicio.dto.OrderItemDto;
import com.ejercicio.entities.OrderItem;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.OrderItemMapper;
import com.ejercicio.repositories.OrderItemRepository;
import com.ejercicio.services.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public List<OrderItemDto> findAll() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        return orderItemList.stream().map(orderItemMapper::orderItemToOrderItemDto).toList();
    }

    @Override
    public OrderItemDto findById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));
        return orderItemMapper.orderItemToOrderItemDto(orderItem);
    }

    @Override
    public OrderItemDto save(OrderItemDto entityDto) {
        OrderItem entity = orderItemMapper.orderItemDtoToOrderItem(entityDto);
        return orderItemMapper.orderItemToOrderItemDto(orderItemRepository.save(entity));
    }

    @Override
    public OrderItemDto update(Long id, OrderItemDto entityDto) {
        OrderItem entity = orderItemMapper.orderItemDtoToOrderItem(entityDto);
        OrderItem data = orderItemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));
        data.updateWith(entity);
        orderItemRepository.save(data);
        return orderItemMapper.orderItemToOrderItemDto(data);
    }

    @Override
    public void deleteById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        orderItemRepository.delete(orderItem);
    }

    @Override
    public List<OrderItemDto> findByOrderId(Long idOrder) {
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(idOrder);
        return orderItemList.stream().map(orderItemMapper::orderItemToOrderItemDto).toList();
    }

    @Override
    public List<OrderItemDto> findByProductId(Long idProduct) {
        List<OrderItem> orderItemList = orderItemRepository.findByProductId(idProduct);
        return orderItemList.stream().map(orderItemMapper::orderItemToOrderItemDto).toList();
    }

    @Override
    public Double sumAllProductSales(Long idProduct) {
        return orderItemRepository.sumAllProductSales(idProduct);
    }
}
