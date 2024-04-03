package com.ejercicio.service;

import com.ejercicio.dto.ShippingDetailsDto;
import com.ejercicio.entities.ShippingDetails;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.ShippingDetailsMapper;
import com.ejercicio.repositories.ShippingDetailsRepository;
import com.ejercicio.services.impl.ShippingDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShippingDetailsServiceTest {
    @Mock
    private ShippingDetailsRepository shippingDetailsRepository;

    @Mock
    private ShippingDetailsMapper shippingDetailsMapper;

    @InjectMocks
    private ShippingDetailsServiceImpl shippingDetailsService;

    ShippingDetails shippingDetails;

    @BeforeEach
    void setUp(){
        shippingDetails = ShippingDetails.builder()
                .id(1L)
                .address("Calle 5")
                .shippingCompany("Benitez")
                .numberReference(123)
                .build();
    }

    @Test
    void ShippingDetailsService_FindAll_ShouldGetAllShippingDetailss() {
        List<ShippingDetails> shippingDetailsList = new ArrayList<>();
        shippingDetailsList.add(shippingDetails);

        given(shippingDetailsRepository.findAll())
                .willReturn(shippingDetailsList);

        List<ShippingDetailsDto> shippingDetailsDtoList = shippingDetailsService.findAll();

        assertNotNull(shippingDetailsDtoList);
    }

    @Test
    void givenShippingDetails_whenFindById_thenReturnShippingDetails(){
        Long idShippingDetails = 1L;

        given(shippingDetailsRepository.findById(idShippingDetails))
                .willReturn(Optional.of(shippingDetails));

        ShippingDetailsDto shippingDetailsDto = new ShippingDetailsDto();

        given(shippingDetailsMapper.shippingDetailsToShippingDetailsDto(any(ShippingDetails.class)))
                .willReturn(shippingDetailsDto);

        ShippingDetailsDto result = shippingDetailsService.findById(idShippingDetails);

        assertNotNull(result);
    }

    @Test
    void givenShippingDetails_whenIdNotFound_thenReturnException() {
        given(shippingDetailsRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            shippingDetailsService.findById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenShippingDetails_whenUpdate_thenReturnShippingDetailsdated() {
        ShippingDetailsDto shippingDetails = new ShippingDetailsDto();
        shippingDetails.setAddress("Calle 2");
        shippingDetails.setShippingCompany("Benitez");
        shippingDetails.setNumberReference(123);

        Long idShippingDetails = 1L;

        given(shippingDetailsRepository.findById(idShippingDetails))
                .willReturn(Optional.of(this.shippingDetails));

        assertDoesNotThrow(() -> {
            shippingDetailsService.update(idShippingDetails, shippingDetails);
        });
    }

    @Test
    void givenAnId_whenShippingDetailsExist_thenDeleteShippingDetails() {
        Long idShippingDetails = 1L;

        given(shippingDetailsRepository.findById(idShippingDetails))
                .willReturn(Optional.of(shippingDetails));

        willDoNothing().given(shippingDetailsRepository).delete(any());

        shippingDetailsService.deleteById(idShippingDetails);

        verify(shippingDetailsRepository, times(1)).delete(any());
    }
}
