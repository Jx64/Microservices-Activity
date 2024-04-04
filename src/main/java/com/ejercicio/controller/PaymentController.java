package com.ejercicio.controller;

import com.ejercicio.dto.PaymentDto;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok().body(paymentService.findById(id));
        }catch (DataNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date")
    public ResponseEntity<?> getByName(@RequestParam LocalDate start, @RequestParam LocalDate end){
        List<PaymentDto> payment = paymentService.searchByFecha(start, end);
        if (payment.isEmpty()){
            return ResponseEntity.badRequest().body("Payment not found");
        }
        return ResponseEntity.ok().body(payment);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getByName(@PathVariable Long id, @RequestParam String method){
        List<PaymentDto> payment = paymentService.searchByPedidoAndPaymentMethod(id, method);
        if (payment.isEmpty()){
            return ResponseEntity.badRequest().body("Payment not found");
        }
        return ResponseEntity.ok().body(payment);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok().body(paymentService.save(paymentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok().body(paymentService.update(id, paymentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        paymentService.deleteById(id);
        return ResponseEntity.ok().body("Payment deleted");
    }
}
