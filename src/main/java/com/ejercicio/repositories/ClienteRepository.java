package com.ejercicio.repositories;

import java.util.List;

public interface ClienteRepository extends Repository<Cliente> {
    Cliente findByEmail(String email);
    List<Cliente> findByDireccion(String direccion);
    List<Cliente> findByNombreStartsWithIgnoreCase(String nombre);
}
