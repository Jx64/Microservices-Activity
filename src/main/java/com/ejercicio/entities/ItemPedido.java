package com.ejercicio.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "itemPedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer cantidad;

    private Double precioUnitario;

    @ManyToOne @JoinColumn(name = "pedidoId")
    private Pedido pedidoId;

    @ManyToOne @JoinColumn(name = "productoId")
    private Producto productoId;
}