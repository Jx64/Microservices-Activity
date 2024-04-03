package com.ejercicio.entities;

import com.ejercicio.entities.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double totalPago;

    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @OneToOne @JoinColumn(name = "pedidoId")
    private Pedido pedidoId;
}