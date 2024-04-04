package com.ejercicio.dto;

import com.ejercicio.entities.Order;
import com.ejercicio.entities.Product;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Integer amount;
    private Double unitPrice;
    private Order fkOrder;
    private Product fkProduct;
}
