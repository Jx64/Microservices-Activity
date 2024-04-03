package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.entities.Order;
import com.ejercicio.entities.ShippingDetails;
import com.ejercicio.entities.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShippingDetailsRepositoryTest extends AbstractIntegrationDBTest {
    ShippingDetailsRepository shippingDetailsRepository;
    OrderRepository orderRepository;

    @Autowired
    public ShippingDetailsRepositoryTest(ShippingDetailsRepository shippingDetailsRepository, OrderRepository orderRepository) {
        this.shippingDetailsRepository = shippingDetailsRepository;
        this.orderRepository = orderRepository;
    }

    @BeforeEach
    void setUp(){
        shippingDetailsRepository.deleteAll();
        orderRepository.deleteAll();
    }

    ShippingDetails shippingDetails, shippingDetails2, shippingDetails3, shippingDetails4;

    void initMockShippingDetails(){
        shippingDetails = ShippingDetails.builder()
                .address("Calle 5")
                .shippingCompany("Benitez")
                .numberReference(123)
                .build();

        shippingDetailsRepository.save(shippingDetails);

        shippingDetails2 = ShippingDetails.builder()
                .address("Calle 7")
                .shippingCompany("MrFast")
                .numberReference(456)
                .build();

        shippingDetailsRepository.save(shippingDetails2);

        shippingDetails3 = ShippingDetails.builder()
                .address("Calle 7")
                .shippingCompany("MrFast")
                .numberReference(456)
                .build();

        shippingDetailsRepository.save(shippingDetails3);

        shippingDetails4 = ShippingDetails.builder()
                .address("Calle 7")
                .shippingCompany("Caracol")
                .numberReference(456)
                .build();

        shippingDetailsRepository.save(shippingDetails4);

        shippingDetailsRepository.flush();
    }

    @Test
    void getAllShipingDetails(){
        initMockShippingDetails();

        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findAll();

        assertThat(shippingDetailsList).hasSize(4);
    }

    @Test
    void givenOrderId_whenFoundId_thenReturnShoppingDetails() {
        initMockShippingDetails();

        Order order = Order.builder()
                .orderDate(LocalDate.of(2023, 11, 23))
                .status(OrderStatus.ENTREGADO)
                .build();
        Order order_1 = orderRepository.save(order);

        orderRepository.flush();

        shippingDetails.setFkOrder(order_1);

        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findByOrderId(order.getId());

        assertThat(shippingDetailsList).isNotEmpty();
    }

    @Test
    void givenShippingCarrier_whenFoundByName_thenReturnShoppingDetails() {
        initMockShippingDetails();

        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findByShippingCompany("MrFast");

        assertThat(shippingDetailsList).isNotEmpty();
        assertThat(shippingDetailsList).hasSize(2);
    }

    @Test
    void givenAnOrderStatus_whenStatusExistAndFound_thenReturnShoppingDetails() {
        initMockShippingDetails();

        Order order = Order.builder()
                .orderDate(LocalDate.of(2023, 11, 23))
                .status(OrderStatus.ENTREGADO)
                .build();
        Order order_1 = orderRepository.save(order);

        Order order2 = Order.builder()
                .orderDate(LocalDate.of(2024, 1, 10))
                .status(OrderStatus.ENTREGADO)
                .build();
        Order order_21 = orderRepository.save(order2);

        orderRepository.flush();

        shippingDetails.setFkOrder(order_1);
        shippingDetails4.setFkOrder(order_21);

        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findByOrderStatus("ENTREGADO");

        assertThat(shippingDetailsList).isNotEmpty();
        assertThat(shippingDetailsList).hasSize(2);
        assertThat(shippingDetailsList).extracting("shippingCompany").contains("Benitez", "Caracol");
    }
}
