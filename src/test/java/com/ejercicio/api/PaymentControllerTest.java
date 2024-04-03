package com.ejercicio.api;

import com.ejercicio.controller.PaymentController;
import com.ejercicio.dto.OrderItemDto;
import com.ejercicio.dto.PaymentDto;
import com.ejercicio.entities.enums.PaymentMethod;
import com.ejercicio.services.PaymentService;
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

@WebMvcTest(controllers = PaymentController.class)
@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllClients_thenReturnListClients() throws Exception {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(1L);
        paymentDto.setPaymentDate(LocalDate.of(2024, 1, 15));
        paymentDto.setPaymentTotal(500);
        paymentDto.setPaymentMethod(PaymentMethod.EFECTIVO);

        List<PaymentDto> paymentList = new ArrayList<>();

        paymentList.add(paymentDto);

        when(paymentService.findAll()).thenReturn(paymentList);

        mockMvc.perform(get("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}

