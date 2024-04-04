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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    void getAllClients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    void whenSave_thenReturns200AndBody() throws Exception {
        ClientDto client = new ClientDto();
        client.setId(1L);
        client.setName("Diego");
        client.setEmail("diego@mail.com");
        client.setAddress("Calle 1");

        when(clientService.save(client)).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/clients")
                        .content(new ObjectMapper().writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Diego"));
    }

    @Test
    void givenAnId_whenDeleteById_thenReturns200() throws Exception {
        ClientDto client = new ClientDto();
        client.setId(1L);
        client.setName("Diego");
        client.setEmail("diego@mail.com");
        client.setAddress("Calle 1");

        doNothing().when(clientService).deleteById(client.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/clients/{id}", client.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
