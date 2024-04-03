package com.ejercicio.services;

import com.ejercicio.dto.ClienteDto;
import com.ejercicio.dto.mapper.ClienteMapper;
import com.ejercicio.entities.Cliente;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.ClienteRepository;
import com.ejercicio.services.impl.ClienteServiceImpl;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    Cliente cliente;

    @BeforeEach
    void setUp(){
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Diego")
                .email("diegolife@gmail.com")
                .direccion("Calle 1")
                .build();
    }

    @Test
    void ClienteService_FindAll_ShouldGetAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);

        given(clienteRepository.findAll())
                .willReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.findAll();

        assertNotNull(clienteDtos);
    }

    @Test
    void givenCliente_whenFindClienteById_thenReturnCliente(){
        Long test1 = 1L;

        given(clienteRepository.findById(test1))
                .willReturn(Optional.of(cliente));

        ClienteDto clienteDto = new ClienteDto();

        given(clienteMapper.clienteToClienteDto(any(Cliente.class)))
                .willReturn(clienteDto);

        ClienteDto result = clienteService.findById(test1);

        assertNotNull(result);
    }

    @Test
    void givenNullOrEmptyClienteId_whenIdNotFound_thenReturnException() {
        given(clienteRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            clienteService.findById(any());
        }, "Data wasn't found");
    }

    @Test
        void givenCliente_whenUpdateCliente_thenUpdateCliente() {
        Cliente clienteUpdate = Cliente.builder()
                .nombre("Diego")
                .email("diegolife@gmail.com")
                .direccion("Calle 2")
                .build();

        Long test1 = 1L;

        given(clienteRepository.findById(test1))
                .willReturn(Optional.of(cliente));

        assertDoesNotThrow(() -> {
            clienteService.update(test1, clienteUpdate);
        });
    }

    @Test
    void givenAnId_whenClienteExist_thenDeleteCliente() {
        Long clienteId = 1L;

        given(clienteRepository.findById(clienteId))
                .willReturn(Optional.of(cliente));

        willDoNothing().given(clienteRepository).delete(any());

        clienteService.deleteById(clienteId);

        verify(clienteRepository, times(1)).delete(any());
    }
}
