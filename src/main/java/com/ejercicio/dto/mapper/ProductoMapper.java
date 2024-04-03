package com.ejercicio.dto.mapper;

import com.ejercicio.dto.ProductoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductoMapper {
    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "stock", target = "stock")
    ProductoDto productoToProductoDto(Producto producto);
    Producto productoDtoToProducto(ProductoDto productoDto);
}
