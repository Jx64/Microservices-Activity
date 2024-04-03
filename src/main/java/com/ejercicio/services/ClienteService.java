package com.ejercicio.services;

import com.ejercicio.dto.ClienteDto;

import java.util.List;

public interface ClienteService {
    List<ClienteDto> findAll();
    ClienteDto findById(Long id);
    ClienteDto save(Cliente e);
    ClienteDto update(Long id, Cliente e);
    void deleteById(Long id);
    ClienteDto findByEmail(String email);
    List<ClienteDto> findByDireccion(String direccion);
    List<ClienteDto> findByNombreStartsWithIgnoreCase(String nombre);
}
