package com.ejercicio.services.impl;

import com.ejercicio.dto.PaymentDto;
import com.ejercicio.entities.Payment;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.PaymentMapper;
import com.ejercicio.repositories.PaymentRepository;
import com.ejercicio.services.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentDto> findAll() {
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentList.stream().map(paymentMapper::paymentToPaymentDto).toList();
    }

    @Override
    public PaymentDto findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));
        return paymentMapper.paymentToPaymentDto(payment);
    }

    @Override
    public PaymentDto save(PaymentDto entityDto) {
        Payment entity = paymentMapper.paymentDtoToPayment(entityDto);
        return paymentMapper.paymentToPaymentDto(paymentRepository.save(entity));
    }

    @Override
    public PaymentDto update(Long id, PaymentDto entityDto) {
        Payment entity = paymentMapper.paymentDtoToPayment(entityDto);
        Payment data = paymentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));
        Payment entityUpdate = data;
        data = paymentRepository.save(entity);
        return paymentMapper.paymentToPaymentDto(data);
    }

    @Override
    public void deleteById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentDto> searchByFecha(LocalDate start, LocalDate end) {
        List<Payment> paymentList = paymentRepository.findByPaymentDateBetween(start, end);
        return paymentList.stream().map(paymentMapper::paymentToPaymentDto).toList();
    }

    @Override
    public List<PaymentDto> searchByPedidoAndPaymentMethod(Long idOrder, String paymentMethod) {
        List<Payment> paymentList = paymentRepository.findByOrderIdAndPaymentMethod(idOrder, paymentMethod);
        return paymentList.stream().map(paymentMapper::paymentToPaymentDto).toList();
    }
}
