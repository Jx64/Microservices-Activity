package com.ejercicio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @ManyToOne @JoinColumn(name = "fkOrder")
    private Order fkOrder;

    @ManyToOne @JoinColumn(name = "fkProduct")
    private Product fkProduct;
}
