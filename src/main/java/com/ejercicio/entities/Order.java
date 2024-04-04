package com.ejercicio.entities;

import com.ejercicio.entities.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_date",nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "fkClient")
    private Client fkClient;

    @OneToMany(mappedBy = "fkOrder")
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "fkOrder")
    private Payment payment;

    @OneToOne(mappedBy = "fkOrder")
    private ShippingDetails shippingDetails;

    public void updateWith(Order entity){
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
