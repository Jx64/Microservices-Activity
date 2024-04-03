package com.ejercicio.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "detalleEnvios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DetalleEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String direccion;

    private String transportadora;

    private Integer numeroGuia;

    @ManyToOne @JoinColumn(name = "pedidoId")
    private Pedido pedidoId;
}