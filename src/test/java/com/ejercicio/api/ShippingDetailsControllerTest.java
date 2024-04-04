package com.ejercicio.api;

import com.ejercicio.controller.ShippingDetailsController;
import com.ejercicio.dto.ShippingDetailsDto;
import com.ejercicio.services.ShippingDetailsService;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShippingDetailsController.class)
@ExtendWith(MockitoExtension.class)
public class ShippingDetailsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ShippingDetailsService shippingDetailsService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllClients_thenReturnListClients() throws Exception {
        ShippingDetailsDto shippingDetails = new ShippingDetailsDto();
        shippingDetails.setId(1L);
        shippingDetails.setAddress("Calle 7");
        shippingDetails.setShippingCompany("MrFast");
        shippingDetails.setNumberReference(123);

        List<ShippingDetailsDto> shippingDetailsList = new ArrayList<>();

        shippingDetailsList.add(shippingDetails);

        when(shippingDetailsService.findAll()).thenReturn(shippingDetailsList);

        mockMvc.perform(get("/api/v1/shippingDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void whenSave_thenReturns200AndBody() throws Exception {
        ShippingDetailsDto shippingDetails = new ShippingDetailsDto();
        shippingDetails.setId(1L);
        shippingDetails.setAddress("Calle 7");
        shippingDetails.setShippingCompany("MrFast");
        shippingDetails.setNumberReference(123);

        when(shippingDetailsService.save(shippingDetails)).thenReturn(shippingDetails);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/shippingDetails")
                        .content(new ObjectMapper().writeValueAsString(shippingDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void givenAnId_whenDeleteById_thenReturns200() throws Exception {
        ShippingDetailsDto shippingDetails = new ShippingDetailsDto();
        shippingDetails.setId(1L);
        shippingDetails.setAddress("Calle 7");
        shippingDetails.setShippingCompany("MrFast");
        shippingDetails.setNumberReference(123);

        doNothing().when(shippingDetailsService).deleteById(shippingDetails.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/shippingDetails/{id}", shippingDetails.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
