package com.ejercicio.api;

import com.ejercicio.controller.ProductController;
import com.ejercicio.dto.ProductDto;
import com.ejercicio.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllProducts_thenReturnListProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    void whenSave_thenReturns200AndBody() throws Exception {
        ProductDto product = new ProductDto();
        product.setId(1L);
        product.setName("fab");
        product.setStock(2);
        product.setPrice(23500D);

        when(productService.save(product)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products")
                        .content(new ObjectMapper().writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("fab"));
    }

    @Test
    void givenAnId_whenDeleteById_thenReturns200() throws Exception {
        ProductDto product = new ProductDto();
        product.setId(1L);
        product.setName("fab");
        product.setStock(2);
        product.setPrice(23500D);

        doNothing().when(productService).deleteById(product.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/products/{id}", product.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
