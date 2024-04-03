package com.ejercicio.repositories;

import com.ejercicio.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:nombre%")
    List<Product> findBySearchTerm(@Param("nombre") String searchTerm);

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findByHaveStock();

    @Query("SELECT p FROM Product p WHERE p.price < :price AND p.stock > 0")
    List<Product> findByPriceLessThanAndHaveStock(@Param("price") Double price);
}
