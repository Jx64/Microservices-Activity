package com.ejercicio.services.impl;

import com.ejercicio.dto.PagoDto;
import com.ejercicio.dto.mapper.PagoMapper;
import com.ejercicio.entities.Pago;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.Producto;
import com.ejercicio.entities.enums.MetodoPago;
import com.ejercicio.exceptions.CannotSaveException;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.PagoRepository;
import com.ejercicio.services.PagoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {
    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    public PagoServiceImpl(PagoRepository pagoRepository,PagoMapper pagoMapper){
        this.pagoRepository = pagoRepository;
        this.pagoMapper = pagoMapper;
    }

    @Override
    public List<PagoDto> findAll() {
        List<Pago> pagos = pagoRepository.findAll();

        if (pagos.isEmpty()) {
            throw new DataNotFoundException("There's not pays");
        }

        return pagos.stream()
                .map(pagoMapper::pagoToPagoDto)
                .toList();
        //return pagoRepository.findAll().stream().map(pago -> pagoMapper.pagoToPagoDto(pago)).toList();
    }

    @Override
    public PagoDto findPagoById(Long id) throws DataNotFoundException{
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("pay wasn't found"));

        return pagoMapper.pagoToPagoDto(pago);
    }

    @Override
    public List<PagoDto> findByPedidoAndMetodoPago(Long idPedido, MetodoPago metodoPago) {
        List<Pago> pagos = pagoRepository.findByPedidoIdAndMetodoPago(idPedido, metodoPago);

        if (pagos.isEmpty()) {
            throw new DataNotFoundException("pay wasn't found");
        }

        return pagos.stream().map(pagoMapper::pagoToPagoDto).toList();
    }

    @Override
    public List<PagoDto> findPagoByStarFechaAndFinalFecha(LocalDate starDate, LocalDate endDate) {
        List<Pago> pagos = pagoRepository.findByFechaPagoBetween(starDate,endDate);

        if(pagos.isEmpty()){
            throw new DataNotFoundException("pay wasn't found");
        }

        return pagos.stream().map(pagoMapper::pagoToPagoDto).toList();
    }

    @Override
    public PagoDto save(Pago pago) {
        if(pago == null){
            throw new CannotSaveException("Invalid pay");
        }
        return pagoMapper.pagoToPagoDto(pagoRepository.save(pago));
    }

    @Override
    public PagoDto update(Long id, Pago pago) {
        return pagoRepository.findById(id).map(pagoDB->{
            pagoDB.setFechaPago(pago.getFechaPago());
            pagoDB.setMetodoPago(pago.getMetodoPago());
            pagoDB.setTotalPago(pago.getTotalPago());
            pagoDB.setPedidoId(pago.getPedidoId());

            return pagoMapper.pagoToPagoDto(pagoRepository.save(pagoDB));
        }).orElseThrow(()-> new DataNotFoundException("pay wasn't found"));
    }

    @Override
    public void deleteById(Long id) {
        Pago pago = pagoRepository.findById(id).orElseThrow(()->new DataNotFoundException("pay wasn't found"));

        pagoRepository.delete(pago);
    }
}
