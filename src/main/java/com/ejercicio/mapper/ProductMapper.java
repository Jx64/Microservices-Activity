package com.ejercicio.mapper;


import com.ejercicio.dto.ProductDto;
import com.ejercicio.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToProductDto(Product product);
    Product productDtoToProduct(ProductDto productDto);
}
