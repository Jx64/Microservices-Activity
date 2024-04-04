package com.ejercicio.entities;

import jakarta.persistence.*;
import lombok.*;
import java.lang.reflect.Field;

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

    public void updateWith(OrderItem entity){
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Object value = field.get(entity);
                if (value != null) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
