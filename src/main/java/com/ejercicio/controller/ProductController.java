package com.ejercicio.controller;

import com.ejercicio.dto.ProductDto;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok().body(productService.findById(id));
        }catch (DataNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        List<ProductDto> product = productService.search(name);
        if (product.isEmpty()){
            return ResponseEntity.badRequest().body("Product not found");
        }
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/stock")
    public ResponseEntity<?> getByHaveStock(){
        List<ProductDto> product = productService.haveStock();
        if (product.isEmpty()){
            return ResponseEntity.badRequest().body("Product not found");
        }
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<?> getByName(@PathVariable Double price){
        List<ProductDto> product = productService.searchForMaxPrice(price);
        if (product.isEmpty()){
            return ResponseEntity.badRequest().body("Product not found");
        }
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok().body(productService.save(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok().body(productService.update(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().body("Product deleted");
    }
}
