package com.ejercicio.services;

import com.ejercicio.dto.PagoDto;
import com.ejercicio.dto.mapper.PagoMapper;
import com.ejercicio.entities.Pago;
import com.ejercicio.entities.enums.MetodoPago;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.PagoRepository;
import com.ejercicio.services.impl.PagoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
class PagoServiceTest {
    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagoServiceImpl pagoService;

    Pago pago;

    @BeforeEach
    void setUp() {
        pago = Pago.builder()
                .id(1L)
                .metodoPago(MetodoPago.NEQUI)
                .fechaPago(LocalDate.now())
                .totalPago(20000D)
                .build();
    }

    @Test
    void PagoService_FindAll_ShouldGetAllPago() {
        List<Pago> pagos = new ArrayList<>();
        pagos.add(pago);

        given(pagoRepository.findAll())
                .willReturn(pagos);

        List<PagoDto> pagoDtos = pagoService.findAll();

        assertNotNull(pagoDtos);
    }

    @Test
    void givenPagoId_whenFindPagoById_thenReturnPago(){
        Long test1 = 1L;

        given(pagoRepository.findById(test1))
                .willReturn(Optional.of(pago));

        PagoDto pagoDto = new PagoDto();

        given(pagoMapper.pagoToPagoDto(any(Pago.class)))
                .willReturn(pagoDto);

        PagoDto result = pagoService.findPagoById(test1);

        assertNotNull(result);
    }

    @Test
    void givenPagoId_whenIdNotFound_thenReturnException() {
        given(pagoRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            pagoService.findPagoById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenPago_whenUpdatePago_thenUpdatePago() {
        Pago pagoUpdate = Pago.builder()
                .metodoPago(MetodoPago.EFECTIVO)
                .fechaPago(LocalDate.now())
                .totalPago(224210D)
                .build();

        Long test1 = 1L;

        given(pagoRepository.findById(test1))
                .willReturn(Optional.of(pago));

        assertDoesNotThrow(() -> {
            pagoService.update(test1, pagoUpdate);
        });
    }

    @Test
    void givenAnId_whenProductoExist_thenDeleteProducto() {
        Long pagoId = 1L;

        given(pagoRepository.findById(pagoId))
                .willReturn(Optional.of(pago));

        willDoNothing().given(pagoRepository).delete(any());

        pagoService.deleteById(pagoId);

        verify(pagoRepository, times(1)).delete(any());
    }
}