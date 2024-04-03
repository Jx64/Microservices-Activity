package com.ejercicio.service;

import com.ejercicio.dto.ProductDto;
import com.ejercicio.entities.Product;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.ProductMapper;
import com.ejercicio.repositories.ProductRepository;
import com.ejercicio.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    Product product;

    @BeforeEach
    void setUp(){
        product = Product.builder()
                .name("Limon")
                .price(20.3)
                .stock(5)
                .build();
    }

    @Test
    void ProductService_FindAll_ShouldGetAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(product);

        given(productRepository.findAll()).willReturn(products);

        List<ProductDto> productDtos = productService.findAll();

        assertNotNull(productDtos);
    }

    @Test
    void givenProductId_whenFindProductById_thenReturnProduct(){
        Long idProduct = 1L;

        given(productRepository.findById(idProduct))
                .willReturn(Optional.of(product));

        ProductDto productDto = new ProductDto();

        given(productMapper.productToProductDto(any(Product.class)))
                .willReturn(productDto);

        ProductDto result = productService.findById(idProduct);

        assertNotNull(result);
    }

    @Test
    void givenProductId_whenIdNotFound_thenReturnException() {
        given(productRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            productService.findById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenProduct_whenUpdateProduct_thenUpdateProduct() {
        ProductDto productUpdateDto = new ProductDto();
        productUpdateDto.setName("Manzana");
        productUpdateDto.setPrice(23.0);
        productUpdateDto.setStock(2);

        Long idProduct = 1L;

        given(productRepository.findById(idProduct))
                .willReturn(Optional.of(product));

        assertDoesNotThrow(() -> {
            productService.update(idProduct, productUpdateDto);
        });
    }

    @Test
    void givenAnId_whenProductExist_thenDeleteProduct() {
        Long productId = 1L;

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        willDoNothing().given(productRepository).delete(any());

        productService.deleteById(productId);

        verify(productRepository, times(1)).delete(any());
    }
}
