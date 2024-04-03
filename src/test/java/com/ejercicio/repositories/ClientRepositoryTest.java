package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.entities.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientRepositoryTest extends AbstractIntegrationDBTest {
    ClientRepository clientRepository;

    @Autowired
    public ClientRepositoryTest(ClientRepository clientRepository) {

        this.clientRepository = clientRepository;
    }

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
    }

    private void initMockClients(){
        Client cliente = Client.builder()
                .id(1L)
                .name("Diego")
                .email("diegolife@gmail.com")
                .address("Calle 1")
                .build();
        clientRepository.save(cliente);

        Client cliente2 = Client.builder()
                .id(2L)
                .name("Jesus")
                .email("jesusd@gmail.com")
                .address("Calle 3")
                .build();
        clientRepository.save(cliente2);

        Client cliente3 = Client.builder()
                .id(3L)
                .name("Juan")
                .email("juanc@gmail.com")
                .address("Calle 1")
                .build();
        clientRepository.save(cliente3);

        clientRepository.flush();
    }

    @Test
    void getAllClientes(){
        initMockClients();

        List<Client> clientes = clientRepository.findAll();

        assertThat(clientes).hasSize(3);
    }

    @Test
    void givenAnString_whenFindByEmail_thenGetClientes(){
        initMockClients();

        Client client = clientRepository.findByEmail("diegolife@gmail.com");

        assertThat(client).hasFieldOrPropertyWithValue("name","Diego");
    }

    @Test
    void givenAnDireccion_whenFindByDireccion_thenGetClientes(){
        initMockClients();

        List<Client> clients = clientRepository.findByAddress("Calle 1");

        assertThat(clients).hasSize(2);
        assertThat(clients).extracting("name").contains("Diego","Juan");
    }

    @Test
    void givenAnName_whenNameStartsWith_thenGetClientes(){
        initMockClients();

        List<Client> clients = clientRepository.findByNameStartsWithIgnoreCase("J");

        assertThat(clients).hasSize(2);
        assertThat(clients).extracting("name").contains("Jesus","Juan");
    }
}
