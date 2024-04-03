package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.entities.Client;
import com.ejercicio.entities.Order;
import com.ejercicio.entities.OrderItem;
import com.ejercicio.entities.Product;
import com.ejercicio.entities.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryTest extends AbstractIntegrationDBTest {

    OrderRepository orderRepository;
    ClientRepository clientRepository;
    OrderItemRepository orderItemRepository;
    ProductRepository productRepository;

    @Autowired
    public OrderRepositoryTest(OrderRepository orderRepository, ClientRepository clientRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @BeforeEach
    public void setUp() {
        orderRepository.deleteAll();
        clientRepository.deleteAll();
        orderItemRepository.deleteAll();
        productRepository.deleteAll();
    }

    Order order1, order2, order3, order4;

    void initMockOrders(){
        order1 = Order.builder()
                .orderDate(LocalDate.of(2023, 11, 23))
                .status(OrderStatus.ENTREGADO)
                .build();
        orderRepository.save(order1);

        order2 = Order.builder()
                .orderDate(LocalDate.of(2024, 3, 20))
                .status(OrderStatus.PENDIENTE)
                .build();
        orderRepository.save(order2);

        order3 = Order.builder()
                .orderDate(LocalDate.of(2024, 2, 3))
                .status(OrderStatus.ENVIADO)
                .build();
        orderRepository.save(order3);

        order4 = Order.builder()
                .orderDate(LocalDate.of(2024, 1, 15))
                .status(OrderStatus.ENTREGADO)
                .build();
        orderRepository.save(order4);

        orderRepository.flush();
    }

    @Test
    void getAllOrders(){
        initMockOrders();

        List<Order> orders = orderRepository.findAll();

        assertThat(orders).hasSize(4);
    }

    @Test
    void givenAnDate_whenDateBetween_thenGetOrders(){
        initMockOrders();

        Client client = Client.builder()
                .name("Diego")
                .email("diegolife@gmail.com")
                .address("Calle 1")
                .build();
        clientRepository.save(client);
        clientRepository.flush();

        order4.setFkClient(client);

        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 20);

        List<Order> orders = orderRepository.findByOrderDateBetween(start, end);

        assertThat(orders).isNotEmpty();
        assertThat(orders).hasSize(1);
        assertThat(orders).extracting("fkClient").extracting("name").contains("Diego");
    }

    @Test
    void givenAnCliente_whenFindByIdAndStatus_thenGetOrders(){
        initMockOrders();

        Client client = Client.builder()
                .name("Jesus")
                .email("jesus@gmail.com")
                .address("Calle 3")
                .build();
        clientRepository.save(client);
        clientRepository.flush();

        order1.setFkClient(client);

        List<Order> orders = orderRepository.findByClientWithStatus(client.getId(), "ENTREGADO");

        assertThat(orders).isNotEmpty();
        assertThat(orders).extracting("fkClient").first().hasFieldOrPropertyWithValue("name","Jesus");
    }

    @Test
    void givenCliente_whenFindPedidoByClienteId_thenGetPedidoWithItems(){
        initMockOrders();
        Client client = Client.builder()
                .name("Pepe")
                .email("pepe@gmail.com")
                .address("Calle 1")
                .build();
        Client client1 = clientRepository.save(client);
        clientRepository.flush();

        Product product = Product.builder()
                .name("Manzana")
                .price(20.3)
                .stock(5)
                .build();
        Product product1 = productRepository.save(product);
        productRepository.flush();

        OrderItem orderItem = OrderItem.builder()
                .amount(2)
                .unitPrice(30.5)
                .fkOrder(order2)
                .fkProduct(product1)
                .build();
        OrderItem orderItem1 = orderItemRepository.save(orderItem);
        orderItemRepository.flush();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);

        order2.setFkClient(client1);
        order2.setOrderItems(orderItems);

        List<Order> pedidos = orderRepository.findByClientWithItemPedido(1L);

        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos).extracting("fkClient").extracting("name").contains("Pepe");
    }
}
