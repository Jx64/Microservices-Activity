package com.ejercicio.dto.mapper;

import com.ejercicio.dto.ClienteDto;
import org.mapstruct.Mapper;

@Mapper
public interface ClienteMapper {
    ClienteDto clienteToClienteDto(Cliente cliente);
    Cliente clienteDtoToCliente(ClienteDto clienteDto);
}
