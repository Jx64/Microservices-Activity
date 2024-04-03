package com.ejercicio.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends Repository<Producto> {
    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
    List<Producto> findBySearchTerm(@Param("nombre") String searchTerm);

    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findByHaveStock();

    @Query("SELECT p FROM Producto p WHERE p.price < :price AND p.stock > 0")
    List<Producto> findByPriceLessThanAndHaveStock(@Param("price") Double price);
}
