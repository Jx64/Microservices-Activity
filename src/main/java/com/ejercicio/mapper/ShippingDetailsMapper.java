package com.ejercicio.mapper;

import com.ejercicio.dto.ShippingDetailsDto;
import com.ejercicio.entities.ShippingDetails;
import org.mapstruct.Mapper;

@Mapper
public interface ShippingDetailsMapper {
    ShippingDetailsDto shippingDetailsToShippingDetailsDto(ShippingDetails shippingDetails);
    ShippingDetails shippingDetailsDtoToShippingDetails(ShippingDetailsDto shippingDetailsDto);
}
