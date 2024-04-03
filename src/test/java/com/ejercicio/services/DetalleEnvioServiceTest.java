package com.ejercicio.services;

import com.ejercicio.dto.DetalleEnvioDto;
import com.ejercicio.dto.mapper.DetalleEnvioMapper;
import com.ejercicio.entities.DetalleEnvio;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.DetalleEnvioRepository;
import com.ejercicio.services.impl.DetalleEnvioImpl;
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
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class DetalleEnvioServiceTest {
    @Mock
    private DetalleEnvioRepository detalleEnvioRepository;

    @Mock
    private DetalleEnvioMapper detalleEnvioMapper;

    @InjectMocks
    private DetalleEnvioImpl detalleEnvioService;

    DetalleEnvio detalleEnvio;

    @BeforeEach
    void setUp(){
        detalleEnvio = DetalleEnvio.builder()
                .direccion("manzana k casa 6")
                .transportadora("interrapidisimo")
                .numeroGuia(12321)
                .build();
    }

    @Test
    void DetalleEnvioService_FindAll_ShouldGetAllDetalleEnvios() {
        List<DetalleEnvio> detalleEnvios = new ArrayList<>();
        detalleEnvios.add(detalleEnvio);

        given(detalleEnvioRepository.findAll()).willReturn(detalleEnvios);

        List<DetalleEnvioDto> detallesDtos = detalleEnvioService.findAll();

        assertNotNull(detallesDtos);
    }

    @Test
    void givenDetalleEnvioId_whenFindDetalleEnvioById_thenReturnDetalleEnvio(){
        Long idDetalleEnvio = 1L;

        given(detalleEnvioRepository.findById(idDetalleEnvio))
                .willReturn(Optional.of(detalleEnvio));

        DetalleEnvioDto detalleDto = new DetalleEnvioDto();

        given(detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(any(DetalleEnvio.class)))
                .willReturn(detalleDto);

        DetalleEnvioDto result = detalleEnvioService.findDetalleEnvioById(idDetalleEnvio);

        assertNotNull(result);
    }

    @Test
    void givenProductId_whenIdNotFound_thenReturnException() {
        given(detalleEnvioRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            detalleEnvioService.findDetalleEnvioById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenCliente_whenUpdateCliente_thenUpdateCliente() {
        DetalleEnvio detalleUpdate = DetalleEnvio.builder()
                .direccion("manzana 14 casa 16")
                .transportadora("coordinadora")
                .numeroGuia(4631)
                .build();

        Long test1 = 1L;

        given(detalleEnvioRepository.findById(test1))
                .willReturn(Optional.of(detalleEnvio));

        assertDoesNotThrow(() -> {
            detalleEnvioService.update(test1, detalleUpdate);
        });
    }

    @Test
    void givenAnId_whenDetalleEnvioExist_thenDeleteDetalleEnvio() {
        Long detalleEnvioId = 1L;

        given(detalleEnvioRepository.findById(detalleEnvioId))
                .willReturn(Optional.of(detalleEnvio));

        willDoNothing().given(detalleEnvioRepository).delete(any());

        detalleEnvioService.deleteById(detalleEnvioId);

        verify(detalleEnvioRepository, times(1)).delete(any());
    }

}
