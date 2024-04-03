package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.src.MockDataInitializer;
import com.ejercicio.entities.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteRepositoryTest extends AbstractIntegrationDBTest {
    ClienteRepository clienteRepository;

    @Autowired
    public ClienteRepositoryTest(ClienteRepository clienteRepository) {

        this.clienteRepository = clienteRepository;
    }

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    private void initMockData() {
        MockDataInitializer.initMockClientes(clienteRepository);
    }

    @Test
    void getAllClientes(){
        initMockData();

        List<Cliente> clientes = clienteRepository.findAll();

        assertThat(clientes).hasSize(3);
    }

    @Test
    void givenAnString_whenFindByEmail_thenGetClientes(){
        initMockData();

        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        assertThat(cliente).hasFieldOrPropertyWithValue("nombre","Diego");
    }

    @Test
    void givenAnDireccion_whenFindByDireccion_thenGetClientes(){
        initMockData();

        List<Cliente> clientes = clienteRepository.findByDireccion("Calle 1");

        assertThat(clientes).hasSize(2);
        assertThat(clientes).extracting("nombre").contains("Diego","Juan");
    }

    @Test
    void givenAnName_whenNameStartsWith_thenGetClientes(){
        initMockData();

        List<Cliente> clientes = clienteRepository.findByNombreStartsWithIgnoreCase("J");

        assertThat(clientes).hasSize(2);
        assertThat(clientes).extracting("nombre").contains("Jesus","Juan");
    }
}
