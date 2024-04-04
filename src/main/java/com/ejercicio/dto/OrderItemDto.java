package com.ejercicio.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Integer amount;
    private Double unitPrice;
    private OrderDto fkOrder;
    private ProductDto fkProduct;
}
