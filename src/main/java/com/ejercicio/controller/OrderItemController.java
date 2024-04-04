package com.ejercicio.controller;

import com.ejercicio.dto.OrderItemDto;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.services.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orderItems")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(orderItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok().body(orderItemService.findById(id));
        }catch (DataNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getByOrder(@PathVariable Long id){
        List<OrderItemDto> orderItem = orderItemService.findByOrderId(id);
        if (orderItem == null){
            return ResponseEntity.badRequest().body("OrderItem not found");
        }
        return ResponseEntity.ok().body(orderItem);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getByProduct(@PathVariable Long id){
        List<OrderItemDto> orderItem = orderItemService.findByProductId(id);
        if (orderItem.isEmpty()){
            return ResponseEntity.badRequest().body("OrderItem not found");
        }
        return ResponseEntity.ok().body(orderItem);
    }

    @GetMapping("/product/sales/{id}")
    public ResponseEntity<?> getProductSales(@PathVariable Long id){
        Double orderItem = orderItemService.sumAllProductSales(id);
        if (orderItem == null){
            return ResponseEntity.badRequest().body("OrderItem not found");
        }
        return ResponseEntity.ok().body(orderItem);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.ok().body(orderItemService.save(orderItemDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.ok().body(orderItemService.update(id, orderItemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderItemService.deleteById(id);
        return ResponseEntity.ok().body("OrderItem deleted");
    }
}

