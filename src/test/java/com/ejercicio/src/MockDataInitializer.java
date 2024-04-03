package com.ejercicio.src;

import com.ejercicio.entities.*;
<<<<<<< HEAD
import com.ejercicio.entities.enums.OrderStatus;
import com.ejercicio.repositories.*;

import java.time.LocalDate;
import java.util.List;

public class MockDataInitializer {
    public static void initMockProducts(ProductRepository productRepository) {
        Product product = Product.builder()
                .name("Cafe")
                .price(200.32)
                .stock(0)
                .build();
        productRepository.save(product);

        Product producto2 = Product.builder()
                .name("Manzana")
                .price(19.6)
                .stock(5)
                .build();
        productRepository.save(producto2);

        Product producto3 = Product.builder()
                .name("Mandarina")
                .price(20.3)
                .stock(0)
                .build();
        productRepository.save(producto3);

        Product producto4 = Product.builder()
                .name("Limon")
                .price(8.04)
                .stock(20)
                .build();
        productRepository.save(producto4);

        productRepository.flush();
    }

    public static void initMockClients(ClientRepository clienteRepository) {
        Client cliente = Client.builder()
                .id(1L)
                .name("Diego")
                .email("diegolife@gmail.com")
                .address("Calle 1")
                .build();
        clienteRepository.save(cliente);

        Client cliente2 = Client.builder()
                .id(2L)
                .name("Jesus")
                .email("jesusd@gmail.com")
                .address("Calle 3")
                .build();
        clienteRepository.save(cliente2);

        Client cliente3 = Client.builder()
                .id(3L)
                .name("Juan")
                .email("juanc@gmail.com")
                .address("Calle 1")
=======
import com.ejercicio.entities.enums.MetodoPago;
import com.ejercicio.entities.enums.PedidoStatus;
import com.ejercicio.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MockDataInitializer {
    public static void initMockClientes(ClienteRepository clienteRepository) {
        Cliente cliente = Cliente.builder()
                .nombre("Diego")
                .email("diegolife@gmail.com")
                .direccion("Calle 1")
                .build();
        clienteRepository.save(cliente);

        Cliente cliente2 = Cliente.builder()
                .nombre("Jesus")
                .email("jesusd@gmail.com")
                .direccion("Calle 3")
                .build();
        clienteRepository.save(cliente2);

        Cliente cliente3 = Cliente.builder()
                .nombre("Juan")
                .email("juanc@gmail.com")
                .direccion("Calle 1")
>>>>>>> origin/main
                .build();
        clienteRepository.save(cliente3);

        clienteRepository.flush();
    }

<<<<<<< HEAD
    public static void initMockOrders(OrderRepository orderRepository, ClientRepository clientRepository) {
        Client client = clientRepository.findByEmail("diegolife@gmail.com");
        Client client2 = clientRepository.findByEmail("jesusd@gmail.com");

        Order order = Order.builder()
                .orderDate(LocalDate.of(2023, 11, 23))
                .status(OrderStatus.ENTREGADO)
                .fkClient(client)
                .build();
        orderRepository.save(order);

        Order order2 = Order.builder()
                .orderDate(LocalDate.of(2024, 3, 20))
                .status(OrderStatus.PENDIENTE)
                .fkClient(client)
                .build();
        orderRepository.save(order2);

        Order pedido3 = Order.builder()
                .orderDate(LocalDate.of(2024, 2, 3))
                .status(OrderStatus.ENVIADO)
                .fkClient(client2)
                .build();
        orderRepository.save(pedido3);

        Order pedido4 = Order.builder()
                .orderDate(LocalDate.of(2024, 1, 15))
                .status(OrderStatus.ENTREGADO)
                .fkClient(client2)
                .build();
        orderRepository.save(pedido4);

        orderRepository.flush();
    }

    public static void initMockOrderItems(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        List<Order> orderList = orderRepository.findByClientWithStatus(1L, "ENTREGADO");
        List<Order> orderList2 = orderRepository.findByClientWithStatus(2L, "ENTREGADO");

        if (!orderList.isEmpty() && !orderList2.isEmpty()) {
            Order order = orderList.get(0);
            Order order2 = orderList2.get(0);

            List<Product> productList = productRepository.findBySearchTerm("Manzana");
            Product product = productList.get(0);

            OrderItem orderItem = OrderItem.builder()
                    .amount(2)
                    .unitPrice(20.3)
                    .fkOrder(order)
                    .fkProduct(product)
                    .build();

            orderItemRepository.save(orderItem);

            OrderItem itemPedido2 = OrderItem.builder()
                    .amount(1)
                    .unitPrice(20.3)
                    .fkOrder(order2)
                    .fkProduct(product)
                    .build();

            orderItemRepository.save(itemPedido2);

            orderItemRepository.flush();
        }
    }
}
=======
    public static void initMockProductos(ProductoRepository productoRepository) {
        Producto producto = Producto.builder()
                .nombre("Cafe")
                .price(200.32)
                .stock(0)
                .build();
        productoRepository.save(producto);

        Producto producto2 = Producto.builder()
                .nombre("Manzana")
                .price(19.6)
                .stock(5)
                .build();
        productoRepository.save(producto2);

        Producto producto3 = Producto.builder()
                .nombre("Mandarina")
                .price(20.3)
                .stock(0)
                .build();
        productoRepository.save(producto3);

        Producto producto4 = Producto.builder()
                .nombre("Limon")
                .price(8.04)
                .stock(20)
                .build();
        productoRepository.save(producto4);

        productoRepository.flush();
    }

    public static void initMockPedidos(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {

        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");
        Cliente cliente2 = clienteRepository.findByEmail("jesusd@gmail.com");

        Pedido pedido = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2023, 11, 23, 10, 30))
                .status(PedidoStatus.ENTREGADO)
                .cliente(cliente)
                .build();
        pedidoRepository.save(pedido);

        Pedido pedido2 = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, 3, 20, 16, 55))
                .status(PedidoStatus.PENDIENTE)
                .cliente(cliente)
                .build();
        pedidoRepository.save(pedido2);

        Pedido pedido3 = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, 2, 3, 7, 4))
                .status(PedidoStatus.ENVIADO)
                .cliente(cliente2)
                .build();
        pedidoRepository.save(pedido3);

        Pedido pedido4 = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, 1, 15, 19, 13))
                .status(PedidoStatus.ENTREGADO)
                .cliente(cliente2)
                .build();
        pedidoRepository.save(pedido4);

        pedidoRepository.flush();
    }


    public static void initMockItemPedido(ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository, ProductoRepository productoRepository, ClienteRepository clienteRepository) {
        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        List<Pedido> pedidoList = pedidoRepository.findByClienteIdAndStatus(cliente.getId(), "ENTREGADO");

        List<Producto> productoList = productoRepository.findBySearchTerm("Manzana");

        Pedido pedido = pedidoList.get(0);
        Producto producto = productoList.get(0);

        ItemPedido itemPedido = ItemPedido.builder()
                .cantidad(2)
                .precioUnitario(20.3)
                .pedido(pedido)
                .producto(producto)
                .build();

        itemPedidoRepository.save(itemPedido);

        Cliente cliente2 = clienteRepository.findByEmail("jesusd@gmail.com");

        List<Pedido> pedidoList2 = pedidoRepository.findByClienteIdAndStatus(cliente2.getId(), "ENTREGADO");

        Pedido pedido2 = pedidoList2.get(0);

        ItemPedido itemPedido2 = ItemPedido.builder()
                .cantidad(1)
                .precioUnitario(20.3)
                .pedido(pedido2)
                .producto(producto)
                .build();

        itemPedidoRepository.save(itemPedido2);

        itemPedidoRepository.flush();
    }

    public static void initMockPago(PedidoRepository pedidoRepository, PagoRepository pagoRepository, ClienteRepository clienteRepository) {
        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        List<Pedido> pedidoList = pedidoRepository.findByClienteIdAndStatus(cliente.getId(), "ENTREGADO");

        Pedido pedido = pedidoList.get(0);

        Pago pago = Pago.builder()
                .totalPago(60.9)
                .fechaPago(LocalDate.of(2023, 11, 23))
                .metodoPago(MetodoPago.TARJETA_CREDITO)
                .pedido(pedido)
                .build();

        pagoRepository.save(pago);

        pagoRepository.flush();
    }

    public static void initMockDetalleEnvio(DetalleEnvioRepository detalleEnvioRepository, ClienteRepository clienteRepository, PedidoRepository pedidoRepository){
        Cliente cliente = clienteRepository.findByEmail("diegolife@gmail.com");

        List<Pedido> pedidoList = pedidoRepository.findByClienteIdAndStatus(cliente.getId(), "ENTREGADO");

        Pedido pedido = pedidoList.get(0);

        DetalleEnvio detalleEnvio = DetalleEnvio.builder()
                .direccion("Calle 2")
                .transportadora("Benitez")
                .numeroGuia(123)
                .pedido(pedido)
                .build();

        detalleEnvioRepository.save(detalleEnvio);

        detalleEnvioRepository.flush();
    }
}
>>>>>>> origin/main
