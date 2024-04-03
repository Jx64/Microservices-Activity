package com.ejercicio.mapper;

import com.ejercicio.dto.ClientDto;
import com.ejercicio.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto clientToClientDto(Client client);
    Client clientDtoToCliente(ClientDto clientDto);
}
