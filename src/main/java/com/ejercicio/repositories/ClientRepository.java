package com.ejercicio.repositories;

import com.ejercicio.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    List<Client> findByAddress(String address);
    List<Client> findByNameStartsWithIgnoreCase(String name);
}
