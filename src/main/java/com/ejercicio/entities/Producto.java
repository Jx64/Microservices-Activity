package com.ejercicio.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    private Double price;

    private Integer stock;

    @OneToMany(mappedBy = "productoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedido> itemPedidos;
}
