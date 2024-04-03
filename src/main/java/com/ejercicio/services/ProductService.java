package com.ejercicio.services;

import com.ejercicio.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    ProductDto findById(Long id);
    ProductDto save(ProductDto entityDto);
    ProductDto update(Long id, ProductDto entityDto);
    void deleteById(Long id);
    List<ProductDto> search(String searchTerm);
    List<ProductDto> haveStock();
    List<ProductDto> searchForMaxPrice(Double price);
}
