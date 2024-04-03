package com.ejercicio.services.impl;

import com.ejercicio.dto.DetalleEnvioDto;
import com.ejercicio.dto.mapper.DetalleEnvioMapper;
import com.ejercicio.entities.DetalleEnvio;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.enums.PedidoStatus;
import com.ejercicio.exceptions.CannotSaveException;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.DetalleEnvioRepository;
import com.ejercicio.services.DetalleEnvioService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleEnvioImpl implements DetalleEnvioService {

    private final DetalleEnvioRepository detalleEnvioRepository;
    private final DetalleEnvioMapper detalleEnvioMapper;

    public DetalleEnvioImpl(DetalleEnvioRepository detalleEnvioRepository, DetalleEnvioMapper detalleEnvioMapper) {
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.detalleEnvioMapper = detalleEnvioMapper;
    }

    @Override
    public List<DetalleEnvioDto> findAll() {
        List<DetalleEnvio> dataList = detalleEnvioRepository.findAll();

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(detalleEnvioMapper::detalleEnvioToDetalleEnvioDto)
                .toList();
    }

    @Override
    public DetalleEnvioDto findDetalleEnvioById(Long id) {
        DetalleEnvio entityData = detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(entityData);
    }

    @Override
    public List<DetalleEnvioDto> findDetalleEnvioByPedido(Pedido idPedido) {
        List<DetalleEnvio> dataList = detalleEnvioRepository.findByPedidoId(idPedido);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(detalleEnvioMapper::detalleEnvioToDetalleEnvioDto)
                .toList();
    }

    @Override
    public List<DetalleEnvioDto> findDetalleEnvioByTransportadora(String name) {
        List<DetalleEnvio> detalles = detalleEnvioRepository.findByTransportadora(name);

        if(detalles.isEmpty()){
            throw new DataNotFoundException("DetallesEnvio wasn't found");
        }

        return detalles.stream().map(detalleEnvioMapper::detalleEnvioToDetalleEnvioDto).toList();
    }

    @Override
    public List<DetalleEnvioDto> findDetalleEnvioByEstado(PedidoStatus pedidoStatus) {
        List<DetalleEnvio> detalles = detalleEnvioRepository.findByEstado(pedidoStatus);

        if(detalles.isEmpty()){
            throw new DataNotFoundException("DetalleEnvio wasn't found");
        }

        return detalles.stream().map(detalleEnvioMapper::detalleEnvioToDetalleEnvioDto).toList();
    }

    @Override
    public DetalleEnvioDto save(DetalleEnvio detalleEnvio) {
        if(detalleEnvio == null){
            throw new CannotSaveException("Invalid product");
        }
        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvioRepository.save(detalleEnvio));
    }

    @Override
    public DetalleEnvioDto update(Long id, DetalleEnvio detalleEnvio) {
        DetalleEnvio entityData = detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        BeanUtils.copyProperties(detalleEnvio, entityData);
        detalleEnvioRepository.save(entityData);

        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(entityData);
    }

    @Override
    public void deleteById(Long id) {
        DetalleEnvio e = detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        detalleEnvioRepository.delete(e);
    }
}
