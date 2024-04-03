package com.ejercicio.controller;

import com.ejercicio.dto.OrderDto;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok().body(orderService.findById(id));
        }catch (DataNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date")
    public ResponseEntity<?> getByDate(@RequestParam LocalDate start, @RequestParam LocalDate end){
        List<OrderDto> order = orderService.searchByFecha(start, end);
        if (order == null){
            return ResponseEntity.badRequest().body("Order not found");
        }
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/clientStatus/{id}")
    public ResponseEntity<?> getStatusByClient(@PathVariable Long id, @RequestParam String status){
        List<OrderDto> order = orderService.searchByClienteAndStatus(id, status);
        if (order.isEmpty()){
            return ResponseEntity.badRequest().body("Order not found");
        }
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> getOrderItemsByClient(@PathVariable Long id){
        List<OrderDto> order = orderService.searchClientOrderItems(id);
        if (order.isEmpty()){
            return ResponseEntity.badRequest().body("Order not found");
        }
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.save(orderDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().body(orderService.update(id, orderDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok().body("Order deleted");
    }
}
