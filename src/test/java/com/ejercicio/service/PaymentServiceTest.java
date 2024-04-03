package com.ejercicio.service;

import com.ejercicio.dto.PaymentDto;
import com.ejercicio.entities.Payment;
import com.ejercicio.entities.enums.PaymentMethod;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.PaymentMapper;
import com.ejercicio.repositories.PaymentRepository;
import com.ejercicio.services.impl.PaymentServiceImpl;
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
public class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    Payment payment;

    @BeforeEach
    void setUp(){
        LocalDate date = LocalDate.of(2024,1,23);

        payment = Payment.builder()
                .id(1L)
                .paymentTotal(200)
                .paymentDate(date)
                .paymentMethod(PaymentMethod.NEQUI)
                .build();
    }

    @Test
    void PaymentService_FindAll_ShouldGetAllPayment() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);

        given(paymentRepository.findAll())
                .willReturn(paymentList);

        List<PaymentDto> paymentDto = paymentService.findAll();

        assertNotNull(paymentDto);
    }

    @Test
    void givenPayment_whenFindById_thenReturnPayment(){
        Long idPayment = 1L;

        given(paymentRepository.findById(idPayment))
                .willReturn(Optional.of(payment));

        PaymentDto orderItemDto = new PaymentDto();

        given(paymentMapper.paymentToPaymentDto(any(Payment.class)))
                .willReturn(orderItemDto);

        PaymentDto result = paymentService.findById(idPayment);

        assertNotNull(result);
    }

    @Test
    void givenPayment_whenIdNotFound_thenReturnException() {
        given(paymentRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            paymentService.findById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenPayment_whenUpdate_thenReturnPaymentUpdated() {
        LocalDate new_date = LocalDate.of(2024,2,23);
        PaymentDto paymentUpdate = new PaymentDto();
        paymentUpdate.setPaymentTotal(200);
        paymentUpdate.setPaymentDate(new_date);
        paymentUpdate.setPaymentMethod(PaymentMethod.NEQUI);


        Long idPayment = 1L;

        given(paymentRepository.findById(idPayment))
                .willReturn(Optional.of(payment));

        assertDoesNotThrow(() -> {
            paymentService.update(idPayment, paymentUpdate);
        });
    }

    @Test
    void givenAnId_whenPaymentExist_thenDeletePayment() {
        Long idPayment = 1L;

        given(paymentRepository.findById(idPayment))
                .willReturn(Optional.of(payment));

        willDoNothing().given(paymentRepository).delete(any());

        paymentService.deleteById(idPayment);

        verify(paymentRepository, times(1)).delete(any());
    }
}
