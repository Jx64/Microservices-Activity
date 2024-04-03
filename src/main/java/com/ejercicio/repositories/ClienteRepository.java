package com.ejercicio.repositories;

import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends Repository<Cliente> {
    Cliente findByEmail(String email);
    List<Cliente> findByDireccion(String direccion);
    List<Cliente> findByNombreStartsWithIgnoreCase(String nombre);
}
