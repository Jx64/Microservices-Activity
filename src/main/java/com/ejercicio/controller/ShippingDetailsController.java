package com.ejercicio.controller;

import com.ejercicio.dto.ShippingDetailsDto;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.services.ShippingDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shippingDetails")
public class ShippingDetailsController {
    private final ShippingDetailsService shippingDetailsService;

    public ShippingDetailsController(ShippingDetailsService shippingDetailsService) {
        this.shippingDetailsService = shippingDetailsService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(shippingDetailsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok().body(shippingDetailsService.findById(id));
        }catch (DataNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getByOrder(@PathVariable Long id){
        List<ShippingDetailsDto> shippingDetails = shippingDetailsService.searchByOrderId(id);
        if (shippingDetails == null){
            return ResponseEntity.badRequest().body("Shipping details not found");
        }
        return ResponseEntity.ok().body(shippingDetails);
    }

    @GetMapping("/shippingCompany/{name}")
    public ResponseEntity<?> getByShippingCompany(@PathVariable String name){
        List<ShippingDetailsDto> shippingDetails = shippingDetailsService.searchByShippingCompanyName(name);
        if (shippingDetails.isEmpty()){
            return ResponseEntity.badRequest().body("Shipping details not found");
        }
        return ResponseEntity.ok().body(shippingDetails);
    }

    @GetMapping("/order/status/{status}")
    public ResponseEntity<?> getByOrderStatus(@PathVariable String status){
        List<ShippingDetailsDto> shippingDetails = shippingDetailsService.searchByOrderStatus(status);
        if (shippingDetails == null){
            return ResponseEntity.badRequest().body("Shipping details not found");
        }
        return ResponseEntity.ok().body(shippingDetails);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ShippingDetailsDto shippingDetailsDto) {
        return ResponseEntity.ok(shippingDetailsService.save(shippingDetailsDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ShippingDetailsDto shippingDetailsDto) {
        return ResponseEntity.ok().body(shippingDetailsService.update(id, shippingDetailsDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        shippingDetailsService.deleteById(id);
        return ResponseEntity.ok().body("Shipping details deleted");
    }
}
