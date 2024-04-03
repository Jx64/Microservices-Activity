package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.Pago;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.enums.MetodoPago;
import com.ejercicio.src.MockDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PagoRepositoryTest extends AbstractIntegrationDBTest {
    PagoRepository pagoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public PagoRepositoryTest(PagoRepository pagoRepository){
        this.pagoRepository = pagoRepository;
    }

    @BeforeEach
    void setUp(){
        pagoRepository.deleteAll();
    }

    void initMockData(){
        MockDataInitializer.initMockClientes(clienteRepository);
        MockDataInitializer.initMockProductos(productoRepository);
        MockDataInitializer.initMockPedidos(pedidoRepository, clienteRepository);
        MockDataInitializer.initMockItemPedido(itemPedidoRepository, pedidoRepository, productoRepository, clienteRepository);
        MockDataInitializer.initMockPago(pedidoRepository, pagoRepository, clienteRepository);
    }

    @Test
    void getAllPagos(){
        initMockData();

        List<Pago> pagos = pagoRepository.findAll();

        assertThat(pagos).isNotEmpty();
        assertThat(pagos).hasSize(1);
        assertThat(pagos).first().hasFieldOrPropertyWithValue("totalPago", 60.9);
        assertThat(pagos).extracting("pedidoId").extracting("clienteId").extracting("nombre").contains("Diego");
    }

    @Test
    void givenFechaPago_whenFindFechaBetweenTwoDates_thenGetPagos(){
        initMockData();

        LocalDate start = LocalDate.of(2023, 10, 1);
        LocalDate end = LocalDate.of(2024, 1, 27);

        List<Pago> pagos = pagoRepository.findByFechaPagoBetween(start, end);

        LocalDate dateTest = LocalDate.of(2023, 11, 23);

        assertThat(pagos).isNotEmpty();
        assertThat(pagos).first().hasFieldOrPropertyWithValue("fechaPago", dateTest);
    }

    @Test
    void givenAnPedidoAndMetodoPago_whenFindByIdAndMetodo_thenGetPagos(){
        initMockData();

        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        List<Pedido> pedidos = pedidoRepository.findByClienteWithItemPedido(cliente.getId());

        Pedido pedido = pedidos.get(0);

        List<Pago> pagos = pagoRepository.findByPedidoIdAndMetodoPago(pedido.getId(), MetodoPago.TARJETA_CREDITO);

        assertThat(pagos).isNotEmpty();
        assertThat(pagos).extracting("pedidoId").extracting("clienteId").extracting("nombre").contains("Diego");
    }
}
