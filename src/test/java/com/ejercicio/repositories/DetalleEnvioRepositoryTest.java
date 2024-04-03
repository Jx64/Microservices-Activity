package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.DetalleEnvio;
import com.ejercicio.entities.Pago;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.enums.PedidoStatus;
import com.ejercicio.src.MockDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DetalleEnvioRepositoryTest extends AbstractIntegrationDBTest {
    DetalleEnvioRepository detalleEnvioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    public DetalleEnvioRepositoryTest(DetalleEnvioRepository detalleEnvioRepository){
        this.detalleEnvioRepository = detalleEnvioRepository;
    }

    @BeforeEach
    void setUp(){
        detalleEnvioRepository.deleteAll();
    }

    void initMockData(){
        MockDataInitializer.initMockClientes(clienteRepository);
        MockDataInitializer.initMockPedidos(pedidoRepository, clienteRepository);
        MockDataInitializer.initMockDetalleEnvio(detalleEnvioRepository, clienteRepository, pedidoRepository);
    }

    @Test
    void getAllDetalleEnvio(){
        initMockData();

        List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findAll();

        assertThat(detalleEnvios).isNotEmpty();
    }

    @Test
    void givenPedido_whenFindById_thenGetDetalleEnvio(){
        initMockData();

        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        List<Pedido> pedidos = pedidoRepository.findByClienteIdAndStatus(cliente.getId(), PedidoStatus.PENDIENTE);

        Pedido pedido = pedidos.get(0);

        List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findByPedidoId(pedido);

        assertThat(detalleEnvios).isNotEmpty();
    }

    @Test
    void givenAnTransportadora_whenFindByName_thenGetDetalleEnvio(){
        initMockData();

        List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findByTransportadora("Benitez");

        assertThat(detalleEnvios).hasSize(1);
        assertThat(detalleEnvios).first().extracting("pedidoId").extracting("clienteId").hasFieldOrPropertyWithValue("nombre","Diego");
    }

    @Test
    void givenAnStatus_whenFindByStatusEquals_thenGetAllDetalleEnvio(){
        initMockData();

        List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findByEstado(PedidoStatus.ENTREGADO);

        assertThat(detalleEnvios).hasSize(1);
    }
}
