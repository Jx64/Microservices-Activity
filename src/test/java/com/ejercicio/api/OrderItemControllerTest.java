package com.ejercicio.api;

import com.ejercicio.controller.OrderItemController;
import com.ejercicio.dto.OrderItemDto;
import com.ejercicio.services.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderItemController.class)
@ExtendWith(MockitoExtension.class)
public class OrderItemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllClients_thenReturnListClients() throws Exception {
        OrderItemDto order = new OrderItemDto();
        order.setId(1L);
        order.setUnitPrice(20.3);
        order.setAmount(5);

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();

        orderItemDtoList.add(order);

        when(orderItemService.findAll()).thenReturn(orderItemDtoList);

        mockMvc.perform(get("/api/v1/orderItems")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
