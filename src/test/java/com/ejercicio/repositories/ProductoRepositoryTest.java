package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.src.MockDataInitializer;
import com.ejercicio.entities.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductoRepositoryTest extends AbstractIntegrationDBTest {

    ProductoRepository productoRepository;

    @Autowired
    public ProductoRepositoryTest(ProductoRepository productoRepository) {

        this.productoRepository = productoRepository;
    }

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();
    }

    private void initMockData(){
        MockDataInitializer.initMockProductos(productoRepository);
    }

    @Test
    void getAllProductos(){
        initMockData();

        List<Producto> productos = productoRepository.findAll();

        assertThat(productos).hasSize(4);
    }

    @Test
    void givenTerms_whenFindBySearchTerm_thenGetProductos(){
        initMockData();

        List<Producto> productos = productoRepository.findBySearchTerm("an");


        assertThat(productos).isNotEmpty();
        assertThat(productos).hasSize(2);
        assertThat(productos).extracting("nombre").contains("Manzana","Mandarina");
    }

    @Test
    void shouldGetAllProductsWithStock(){
        initMockData();

        List<Producto> productos = productoRepository.findByHaveStock();

        assertThat(productos).hasSize(2);
        assertThat(productos).first().hasFieldOrPropertyWithValue("nombre","Manzana");
        assertThat(productos).extracting("nombre").contains("Manzana","Limon");
    }

    @Test
    void givenAnPrice_whenFindByPriceLessThan_thenGetProductosWithStock(){
        initMockData();

        List<Producto> productos = productoRepository.findByPriceLessThanAndHaveStock(13.0);

        assertThat(productos).hasSize(1);
        assertThat(productos).first().hasFieldOrPropertyWithValue("nombre","Limon");
    }
}
