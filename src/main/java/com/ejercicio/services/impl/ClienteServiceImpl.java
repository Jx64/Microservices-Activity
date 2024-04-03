package com.ejercicio.services.impl;

import com.ejercicio.dto.ClienteDto;
import com.ejercicio.dto.mapper.ClienteMapper;
import com.ejercicio.exceptions.CannotSaveException;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.ClienteRepository;
import com.ejercicio.services.ClienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public List<ClienteDto> findAll() {
        List<Cliente> dataList = clienteRepository.findAll();

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(clienteMapper::clienteToClienteDto)
                .toList();
    }

    @Override
    public ClienteDto findById(Long id) {
        Cliente entityData = clienteRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        return clienteMapper.clienteToClienteDto(entityData);
    }

    @Override
    public ClienteDto save(Cliente e) {
        if(e == null){
            throw new CannotSaveException("Invalid product");
        }
        return clienteMapper.clienteToClienteDto(clienteRepository.save(e));
    }

    @Override
    public ClienteDto update(Long id, Cliente e) {
        Cliente entityData = clienteRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        BeanUtils.copyProperties(e, entityData);
        clienteRepository.save(entityData);

        return clienteMapper.clienteToClienteDto(entityData);
    }

    @Override
    public void deleteById(Long id) {
        Cliente e = clienteRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        clienteRepository.delete(e);
    }

    @Override
    public ClienteDto findByEmail(String email) {
        Cliente entityData = clienteRepository.findByEmail(email);
        if(entityData == null){
            throw new DataNotFoundException("Product wasn't found");
        }

        return clienteMapper.clienteToClienteDto(entityData);
    }

    @Override
    public List<ClienteDto> findByDireccion(String direccion) {
        List<Cliente> dataList = clienteRepository.findByDireccion(direccion);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(clienteMapper::clienteToClienteDto)
                .toList();
    }

    @Override
    public List<ClienteDto> findByNombreStartsWithIgnoreCase(String nombre) {
        List<Cliente> dataList = clienteRepository.findByNombreStartsWithIgnoreCase(nombre);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(clienteMapper::clienteToClienteDto)
                .toList();
    }
}
