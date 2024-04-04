package com.ejercicio.api;

import com.ejercicio.controller.OrderController;
import com.ejercicio.dto.OrderDto;
import com.ejercicio.entities.enums.OrderStatus;
import com.ejercicio.services.OrderService;
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

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllClients_thenReturnListClients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    void whenSave_thenReturns200AndBody() throws Exception {
        OrderDto orderItem = new OrderDto();
        orderItem.setId(1L);
        orderItem.setOrderDate(LocalDate.now());
        orderItem.setStatus(OrderStatus.ENTREGADO);

        when(orderService.save(orderItem)).thenReturn(orderItem);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/orders")
                        .content(new ObjectMapper().writeValueAsString(orderItem))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
    @Test
    void givenAnId_whenDeleteById_thenReturns200() throws Exception {
        OrderDto order = new OrderDto();
        order.setId(1L);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.ENTREGADO);
        order.setFkClient(null);

        doNothing().when(orderService).deleteById(order.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/orders/{id}", order.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
