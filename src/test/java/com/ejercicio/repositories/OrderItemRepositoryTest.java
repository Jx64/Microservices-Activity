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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class OrderItemRepositoryTest extends AbstractIntegrationDBTest {
    OrderItemRepository orderItemRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    ClientRepository clientRepository;

    @Autowired
    public OrderItemRepositoryTest(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductRepository productRepository, ClientRepository clientRepository){
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @BeforeEach
    void setUp(){
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();
    }

    OrderItem orderItem, orderItem2;

    void initMockOrderItem(){
        orderItem = OrderItem.builder()
                .amount(2)
                .unitPrice(20.3)
                .build();

        orderItemRepository.save(orderItem);

        orderItem2 = OrderItem.builder()
                .amount(3)
                .unitPrice(15.3)
                .build();

        orderItemRepository.save(orderItem2);

        orderItemRepository.flush();
    }

    @Test
    void getAllOrderItems(){
        initMockOrderItem();

        List<OrderItem> orderItemList = orderItemRepository.findAll();

        assertThat(orderItemList).hasSize(2);
    }

    @Test
    void givenAnOrder_whenFindOrderId_thenGetOrderItem() {
        initMockOrderItem();

        Product product = Product.builder()
                .name("Manzana")
                .price(20.3)
                .stock(5)
                .build();
        Product product1 = productRepository.save(product);
        productRepository.flush();

        Order order = Order.builder()
                .orderDate(LocalDate.of(2023, 11, 23))
                .status(OrderStatus.ENTREGADO)
                .build();
        Order order1 = orderRepository.save(order);
        orderRepository.flush();

        orderItem.setFkOrder(order1);
        orderItem.setFkProduct(product1);

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order1.getId());

        assertThat(orderItems).isNotEmpty();
        assertThat(orderItems).extracting("fkProduct").extracting("name").contains("Manzana");
    }

    @Test
    void givenAnProduct_whenFindProductId_thenGetOrderItem() {
        initMockOrderItem();

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

        Order order = Order.builder()
                .orderDate(LocalDate.of(2023, 11, 23))
                .status(OrderStatus.ENTREGADO)
                .build();
        Order order1 = orderRepository.save(order);
        orderRepository.flush();

        order1.setFkClient(client1);

        orderItem.setFkOrder(order1);
        orderItem.setFkProduct(product1);

        List<OrderItem> orderItemList = orderItemRepository.findByProductId(product1.getId());

        assertThat(orderItemList).isNotEmpty();
        assertThat(orderItemList).hasSize(1);
        assertThat(orderItemList).extracting("fkOrder").extracting("fkClient").extracting("name").contains("Pepe");
    }

    @Test
    void givenAnProduct_whenSumAllUnitsPerPrice_thenGetItemSales() {
        initMockOrderItem();

        Product product = Product.builder()
                .name("Manzana")
                .price(20.3)
                .stock(5)
                .build();
        Product product1 = productRepository.save(product);
        productRepository.flush();

        orderItem.setFkProduct(product1);

        Double sumProductSales = orderItemRepository.sumAllProductSales(product1.getId());

        assertThat(sumProductSales).isCloseTo(40.6, withinPercentage(0.01));
    }
}
