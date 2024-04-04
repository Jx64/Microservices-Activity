package com.ejercicio.service;

import com.ejercicio.dto.ClientDto;
import com.ejercicio.dto.ProductDto;
import com.ejercicio.entities.Client;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.ClientMapper;
import com.ejercicio.repositories.ClientRepository;
import com.ejercicio.services.impl.ClientServiceImpl;
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
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    Client client;

    @BeforeEach
    void setUp(){
        client = Client.builder()
                .name("Diego")
                .email("diegolife@gmail.com")
                .address("Calle 1")
                .build();
    }

    @Test
    void ClientService_FindAll_ShouldGetAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(client);

        given(clientRepository.findAll()).willReturn(clients);

        List<ClientDto> productDtos = clientService.findAll();

        assertNotNull(productDtos);
    }

    @Test
    void givenClientId_whenFindClientById_thenReturnClient(){
        Long idProduct = 1L;

        given(clientRepository.findById(idProduct))
                .willReturn(Optional.of(client));

        ClientDto productDto = new ClientDto();

        given(clientMapper.clientToClientDto(any(Client.class)))
                .willReturn(productDto);

        ClientDto result = clientService.findById(idProduct);

        assertNotNull(result);
    }

    @Test
    void givenClientId_whenIdNotFound_thenReturnException() {
        given(clientRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            clientService.findById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenCliente_whenUpdateCliente_thenUpdateCliente() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName("Diego");
        clientDto.setEmail("diego@gmail.com");
        clientDto.setAddress("Calle 1");

        Long test1 = 1L;

        given(clientRepository.findById(test1))
                .willReturn(Optional.of(client));

        assertDoesNotThrow(() -> {
            clientService.update(test1, clientDto);
        });
    }

    @Test
    void givenAnId_whenClienteExist_thenDeleteCliente() {
        Long clienteId = 1L;

        given(clientRepository.findById(clienteId))
                .willReturn(Optional.of(client));

        willDoNothing().given(clientRepository).delete(any());

        clientService.deleteById(clienteId);

        verify(clientRepository, times(1)).delete(any());
    }
}
