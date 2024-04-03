package com.ejercicio.entities;

import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.ItemPedido;
import com.ejercicio.entities.enums.PedidoStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    private PedidoStatus status;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente clienteId;

    @OneToMany(mappedBy = "pedidoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedido> itemPedidos;
}