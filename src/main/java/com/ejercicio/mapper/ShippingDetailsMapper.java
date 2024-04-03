package com.ejercicio.mapper;

import com.ejercicio.dto.ShippingDetailsDto;
import com.ejercicio.entities.ShippingDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingDetailsMapper {
    ShippingDetailsDto shippingDetailsToShippingDetailsDto(ShippingDetails shippingDetails);
    ShippingDetails shippingDetailsDtoToShippingDetails(ShippingDetailsDto shippingDetailsDto);
}
