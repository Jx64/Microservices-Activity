package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.entities.enums.PedidoStatus;
import com.ejercicio.src.MockDataInitializer;
import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PedidoRepositoryTest extends AbstractIntegrationDBTest {
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public PedidoRepositoryTest(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    @BeforeEach
    void setUp(){
        pedidoRepository.deleteAll();
    }

    private void initMockData() {
        MockDataInitializer.initMockClientes(clienteRepository);
        MockDataInitializer.initMockProductos(productoRepository);
        MockDataInitializer.initMockPedidos(pedidoRepository, clienteRepository);
        MockDataInitializer.initMockItemPedido(itemPedidoRepository, pedidoRepository, productoRepository, clienteRepository);
    }

    @Test
    void getAllPedidos(){
        initMockData();

        List<Pedido> pedidos = pedidoRepository.findAll();

        assertThat(pedidos).hasSize(4);
    }

    @Test
    void givenAnDate_whenDateBetween_thenGetPedidos(){
        initMockData();

        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 5, 1, 0, 0);

        List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetween(start, end);

        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos).hasSize(3);
        assertThat(pedidos).extracting("clienteId").extracting("nombre").contains("Diego", "Jesus");
    }

    @Test
    void givenAnCliente_whenFindByIdAndStatus_thenGetPedidos(){
        initMockData();

        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        List<Pedido> pedidos = pedidoRepository.findByClienteIdAndStatus(cliente.getId(), PedidoStatus.PENDIENTE);

        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos).extracting("clienteId").first().hasFieldOrPropertyWithValue("nombre","Diego");
    }

    @Test
    void givenCliente_whenFindPedidoByClienteId_thenGetPedidoWithItems(){
        initMockData();

        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        List<Pedido> pedidos = pedidoRepository.findByClienteWithItemPedido(cliente.getId());

        assertThat(pedidos).isNotEmpty();
    }
}
