package com.ejercicio.services.impl;

import com.ejercicio.dto.ClientDto;
import com.ejercicio.entities.Client;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.ClientMapper;
import com.ejercicio.repositories.ClientRepository;
import com.ejercicio.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper){
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientDto> findAll() {
        List<Client> dataList = clientRepository.findAll();
        return dataList.stream().map(clientMapper::clientToClientDto).toList();
    }

    @Override
    public ClientDto findById(Long id) {
        Client data = clientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        return clientMapper.clientToClientDto(data);
    }

    @Override
    public ClientDto save(ClientDto entityDto) {
        Client entity = clientMapper.clientDtoToCliente(entityDto);
        return clientMapper.clientToClientDto(clientRepository.save(entity));
    }

    @Override
    public ClientDto update(Long id, ClientDto entityDto) {
        Client entity = clientMapper.clientDtoToCliente(entityDto);
        Client data = clientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));
        Client entityUpdate = data;
        data = clientRepository.save(entity);
        return clientMapper.clientToClientDto(data);
    }

    @Override
    public void deleteById(Long id) {
        Client entity = clientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        clientRepository.delete(entity);
    }

    @Override
    public ClientDto searchByEmail(String email) {
        Client data = clientRepository.findByEmail(email);
        return clientMapper.clientToClientDto(data);
    }

    @Override
    public List<ClientDto> searchByAddress(String address) {
        List<Client> dataList = clientRepository.findByAddress(address);
        return dataList.stream().map(clientMapper::clientToClientDto).toList();
    }

    @Override
    public List<ClientDto> searchByName(String name) {
        List<Client> dataList = clientRepository.findByNameStartsWithIgnoreCase(name);
        return dataList.stream().map(clientMapper::clientToClientDto).toList();
    }
}
