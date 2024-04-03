package com.ejercicio.services;

import com.ejercicio.dto.ClientDto;

import java.util.List;

public interface ClientService {
    List<ClientDto> findAll();
    ClientDto findById(Long id);
    ClientDto save(ClientDto entityDto);
    ClientDto update(Long id, ClientDto entityDto);
    void deleteById(Long id);
    ClientDto searchByEmail(String email);
    List<ClientDto> searchByAddress(String address);
    List<ClientDto> searchByName(String name);

}
