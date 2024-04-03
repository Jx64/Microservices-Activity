package com.ejercicio.repositories;

import com.ejercicio.AbstractIntegrationDBTest;
import com.ejercicio.entities.Client;
import com.ejercicio.entities.Order;
import com.ejercicio.entities.Payment;
import com.ejercicio.entities.enums.OrderStatus;
import com.ejercicio.entities.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentRepositoryTest extends AbstractIntegrationDBTest {
    PaymentRepository paymentRepository;
    OrderRepository orderRepository;
    ClientRepository clientRepository;

    @Autowired
    public PaymentRepositoryTest(PaymentRepository paymentRepository, OrderRepository orderRepository, ClientRepository clientRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    @BeforeEach
    void setUp(){
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
    }

    Payment payment;

    void initMockPayments(){
        LocalDate date = LocalDate.of(2024,1,23);
        payment = Payment.builder()
                .paymentTotal(200)
                .paymentDate(date)
                .paymentMethod(PaymentMethod.NEQUI)
                .build();

        paymentRepository.save(payment);

        paymentRepository.flush();
    }

    @Test
    void getAllPayments(){
        initMockPayments();

        List<Payment> paymentList = paymentRepository.findAll();

        assertThat(paymentList).hasSize(1);
    }

    @Test
    void givenFechaPago_whenFindFechaBetweenTwoDates_thenGetPagos(){
        initMockPayments();

        LocalDate start = LocalDate.of(2023, 10, 1);
        LocalDate end = LocalDate.of(2024, 1, 27);

        List<Payment> paymentList = paymentRepository.findByPaymentDateBetween(start, end);

        LocalDate dateTest = LocalDate.of(2024,1,23);

        assertThat(paymentList).isNotEmpty();
        assertThat(paymentList).first().hasFieldOrPropertyWithValue("paymentDate", dateTest);
    }

    @Test
    void givenAnPedidoAndMetodoPago_whenFindByIdAndMetodo_thenGetPagos(){
        initMockPayments();

        Client client = Client.builder()
                .name("Diego")
                .email("diego@gmail.com")
                .address("Calle 1")
                .build();
        Client client1 = clientRepository.save(client);
        clientRepository.flush();

        Order order = Order.builder()
                .orderDate(LocalDate.of(2023, 11, 23))
                .status(OrderStatus.ENTREGADO)
                .build();
        Order order1 = orderRepository.save(order);
        orderRepository.flush();

        order1.setFkClient(client1);
        payment.setFkOrder(order1);

        List<Payment> paymentList = paymentRepository.findByOrderIdAndPaymentMethod(order1.getId(), "NEQUI");

        assertThat(paymentList).isNotEmpty();
        assertThat(paymentList).extracting("fkOrder").extracting("fkClient").extracting("name").contains("Diego");
    }
}
