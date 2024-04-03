package com.ejercicio.dto.mapper;

import com.ejercicio.dto.DetalleEnvioDto;
import com.ejercicio.entities.DetalleEnvio;
import org.mapstruct.Mapper;

@Mapper
public interface DetalleEnvioMapper {
    DetalleEnvioDto detalleEnvioToDetalleEnvioDto(DetalleEnvio detalleEnvio);
    DetalleEnvio detalleEnvioDtoToDetalleEnvio(DetalleEnvioDto detalleEnvioDto);
}

