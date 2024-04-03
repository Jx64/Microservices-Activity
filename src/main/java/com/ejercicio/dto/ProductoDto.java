package com.ejercicio.dto;

import lombok.*;

@Data
public class ProductoDto {
    private Long id;
    private String nombre;
    private Double price;
    private Integer stock;
}
