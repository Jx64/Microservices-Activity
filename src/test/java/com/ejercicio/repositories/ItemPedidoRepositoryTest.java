package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.src.MockDataInitializer;
import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.ItemPedido;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.Producto;
import com.ejercicio.entities.enums.PedidoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class ItemPedidoRepositoryTest extends AbstractIntegrationDBTest {
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    public ItemPedidoRepositoryTest(ItemPedidoRepository itemPedidoRepository){
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @BeforeEach
    void setUp(){
        itemPedidoRepository.deleteAll();
    }

    void initMockData(){
        MockDataInitializer.initMockClientes(clienteRepository);
        MockDataInitializer.initMockProductos(productoRepository);
        MockDataInitializer.initMockPedidos(pedidoRepository, clienteRepository);
        MockDataInitializer.initMockItemPedido(itemPedidoRepository, pedidoRepository, productoRepository, clienteRepository);
    }

    @Test
    void getAllPedidos(){
        initMockData();

        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();

        assertThat(itemPedidos).hasSize(2);
    }

    @Test
    void givenAnPedido_whenFindPedidoId_thenGetItemPedido() {
        initMockData();

        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        List<Pedido> pedidoList = pedidoRepository.findByClienteIdAndStatus(cliente.getId(), PedidoStatus.PENDIENTE);

        Pedido pedido = pedidoList.get(0);

        List<ItemPedido> itemPedidos = itemPedidoRepository.findByPedidoId(pedido.getId());

        assertThat(itemPedidos).isNotEmpty();
        assertThat(itemPedidos).extracting("productoId").extracting("nombre").contains("Manzana");
    }

    @Test
    void givenAnProducto_whenFindProductoId_thenGetItem() {
        initMockData();

        List<Producto> productoList = productoRepository.findBySearchTerm("Manzana");

        Producto producto = productoList.get(0);

        List<ItemPedido> itemPedidos = itemPedidoRepository.findByProductoId(producto.getId());

        assertThat(itemPedidos).isNotEmpty();
        assertThat(itemPedidos).hasSize(2);
        assertThat(itemPedidos).extracting("pedidoId").extracting("clienteId").extracting("nombre").contains("Jesus","Diego");
    }

    @Test
    void givenAnProduct_whenSumAllUnitsPerPrice_thenGetItemSales() {
        initMockData();

        Producto producto = productoRepository.findBySearchTerm("Manzana").get(0);

        Double sumaItemPedidos = itemPedidoRepository.sumaTotalVentasProducto(producto.getId());

        assertThat(sumaItemPedidos).isCloseTo(60.9, withinPercentage(0.01));
    }
}
