package com.ejercicio.api;

import com.ejercicio.controller.ClientController;
import com.ejercicio.dto.ClientDto;
import com.ejercicio.services.ClientService;
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

@WebMvcTest(controllers = ClientController.class)
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClientService clientService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllClients_thenReturnListClients() throws Exception {
        ClientDto client = new ClientDto();
        client.setId(1L);
        client.setName("Limon");
        client.setEmail("diegolife@gmail.com");
        client.setAddress("Calle 1");

        List<ClientDto> clientDtoList = new ArrayList<>();

        clientDtoList.add(client); // Usamos el mapper

        when(clientService.findAll()).thenReturn(clientDtoList);

        mockMvc.perform(get("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
