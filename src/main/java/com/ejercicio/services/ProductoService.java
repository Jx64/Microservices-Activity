package com.ejercicio.services;

import com.ejercicio.dto.ProductoDto;
import com.ejercicio.entities.Producto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoService {
    List<ProductoDto> findAll();
    ProductoDto findById(Long id);
    ProductoDto save(Producto e);
    ProductoDto update(Long id, Producto e);
    void deleteById(Long id);
    List<ProductoDto> findBySearchTerm(@Param("nombre") String searchTerm);
    List<ProductoDto> findByHaveStock();
    List<ProductoDto> findByPriceLessThanAndHaveStock(@Param("price") Double price);
}