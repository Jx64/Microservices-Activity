package com.ejercicio.services;

import com.ejercicio.dto.ShippingDetailsDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShippingDetailsService {
    List<ShippingDetailsDto> findAll();
    ShippingDetailsDto findById(Long id);
    ShippingDetailsDto save(ShippingDetailsDto entityDto);
    ShippingDetailsDto update(Long id, ShippingDetailsDto entityDto);
    void deleteById(Long id);
    List<ShippingDetailsDto> searchByOrderId(@Param("idOrder") Long idOrder);
    List<ShippingDetailsDto> searchByShippingCompanyName(String shippingCompany);
    List<ShippingDetailsDto> searchByOrderStatus(@Param("status") String status);
}
