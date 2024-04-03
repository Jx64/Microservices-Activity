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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        OrderDto order = new OrderDto();
        order.setId(1L);
        order.setOrderDate(LocalDate.of(2024, 1, 15));
        order.setStatus(OrderStatus.ENTREGADO);

        List<OrderDto> orderDtoList = new ArrayList<>();

        orderDtoList.add(order); // Usamos el mapper

        when(orderService.findAll()).thenReturn(orderDtoList);

        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
