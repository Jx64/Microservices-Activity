package com.ejercicio.dto.mapper;

import com.ejercicio.dto.PagoDto;
import org.mapstruct.Mapper;

@Mapper
public interface PagoMapper {
    PagoDto pagoToPagoDto(Pago pago);
    Pago pagoDtoToPago(PagoDto pagoDto);
}
