package com.ejercicio.services.impl;

import com.ejercicio.dto.ShippingDetailsDto;
import com.ejercicio.entities.ShippingDetails;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.ShippingDetailsMapper;
import com.ejercicio.repositories.ShippingDetailsRepository;
import com.ejercicio.services.ShippingDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingDetailsServiceImpl implements ShippingDetailsService {
    private final ShippingDetailsRepository shippingDetailsRepository;
    private final ShippingDetailsMapper shippingDetailsMapper;

    public ShippingDetailsServiceImpl(ShippingDetailsRepository shippingDetailsRepository, ShippingDetailsMapper shippingDetailsMapper) {
        this.shippingDetailsRepository = shippingDetailsRepository;
        this.shippingDetailsMapper = shippingDetailsMapper;
    }

    @Override
    public List<ShippingDetailsDto> findAll() {
        List<ShippingDetails> dataList = shippingDetailsRepository.findAll();
        return dataList.stream().map(shippingDetailsMapper::shippingDetailsToShippingDetailsDto).toList();
    }

    @Override
    public ShippingDetailsDto findById(Long id) {
        ShippingDetails shippingDetails = shippingDetailsRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        return shippingDetailsMapper.shippingDetailsToShippingDetailsDto(shippingDetails);
    }

    @Override
    public ShippingDetailsDto save(ShippingDetailsDto entityDto) {
        ShippingDetails entity = shippingDetailsMapper.shippingDetailsDtoToShippingDetails(entityDto);
        return shippingDetailsMapper.shippingDetailsToShippingDetailsDto(shippingDetailsRepository.save(entity));
    }

    @Override
    public ShippingDetailsDto update(Long id, ShippingDetailsDto entityDto) {
        ShippingDetails entity = shippingDetailsMapper.shippingDetailsDtoToShippingDetails(entityDto);
        ShippingDetails data = shippingDetailsRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));
        ShippingDetails entityUpdate = data;
        data = shippingDetailsRepository.save(entity);
        return shippingDetailsMapper.shippingDetailsToShippingDetailsDto(data);
    }

    @Override
    public void deleteById(Long id) {
        ShippingDetails shippingDetails = shippingDetailsRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        shippingDetailsRepository.delete(shippingDetails);
    }

    @Override
    public List<ShippingDetailsDto> searchByOrderId(Long idOrder) {
        List<ShippingDetails> dataList = shippingDetailsRepository.findByOrderId(idOrder);
        return dataList.stream().map(shippingDetailsMapper::shippingDetailsToShippingDetailsDto).toList();
    }

    @Override
    public List<ShippingDetailsDto> searchByShippingCompanyName(String shippingCompany) {
        List<ShippingDetails> dataList = shippingDetailsRepository.findByShippingCompany(shippingCompany);
        return dataList.stream().map(shippingDetailsMapper::shippingDetailsToShippingDetailsDto).toList();
    }

    @Override
    public List<ShippingDetailsDto> searchByOrderStatus(String status) {
        List<ShippingDetails> dataList = shippingDetailsRepository.findByOrderStatus(status);
        return dataList.stream().map(shippingDetailsMapper::shippingDetailsToShippingDetailsDto).toList();
    }
}
