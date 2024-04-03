package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends AbstractIntegrationDBTest {

    ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    private void initMockProducts(){
        Product product = Product.builder()
                .name("Cafe")
                .price(200.32)
                .stock(0)
                .build();
        productRepository.save(product);

        Product producto2 = Product.builder()
                .name("Manzana")
                .price(19.6)
                .stock(5)
                .build();
        productRepository.save(producto2);

        Product producto3 = Product.builder()
                .name("Mandarina")
                .price(20.3)
                .stock(0)
                .build();
        productRepository.save(producto3);

        Product producto4 = Product.builder()
                .name("Limon")
                .price(8.04)
                .stock(20)
                .build();
        productRepository.save(producto4);

        productRepository.flush();
    }

    @Test
    void getAllProductos(){
        initMockProducts();

        List<Product> productos = productRepository.findAll();

        assertThat(productos).hasSize(4);
    }

    @Test
    void givenTerms_whenFindBySearchTerm_thenGetProductos(){
        initMockProducts();

        List<Product> productos = productRepository.findBySearchTerm("an");


        assertThat(productos).isNotEmpty();
        assertThat(productos).hasSize(2);
        assertThat(productos).extracting("name").contains("Manzana","Mandarina");
    }

    @Test
    void shouldGetAllProductsWithStock(){
        initMockProducts();

        List<Product> productos = productRepository.findByHaveStock();

        assertThat(productos).hasSize(2);
        assertThat(productos).first().hasFieldOrPropertyWithValue("name","Manzana");
        assertThat(productos).extracting("name").contains("Manzana","Limon");
    }

    @Test
    void givenAnPrice_whenFindByPriceLessThan_thenGetProductosWithStock(){
        initMockProducts();

        List<Product> productos = productRepository.findByPriceLessThanAndHaveStock(13.0);

        assertThat(productos).hasSize(1);
        assertThat(productos).first().hasFieldOrPropertyWithValue("name","Limon");
    }
}
