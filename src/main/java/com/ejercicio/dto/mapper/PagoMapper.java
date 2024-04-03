package com.ejercicio.dto.mapper;

import com.ejercicio.dto.PagoDto;
import com.ejercicio.entities.Pago;
import org.mapstruct.Mapper;

@Mapper
public interface PagoMapper {
    PagoDto pagoToPagoDto(Pago pago);
    Pago pagoDtoToPago(PagoDto pagoDto);
}
