package com.ejercicio.controller;

import com.ejercicio.dto.ClientDto;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long idUsuario){
        try {
            return ResponseEntity.ok().body(clientService.findById(idUsuario));
        }catch (DataNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email){
        ClientDto client = clientService.searchByEmail(email);
        if (client == null){
            return ResponseEntity.badRequest().body("Customer not found");
        }
        return ResponseEntity.ok().body(client);
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<?> getByAddress(@PathVariable String address){
        List<ClientDto> client = clientService.searchByAddress(address);
        if (client.isEmpty()){
            return ResponseEntity.badRequest().body("Customer not found");
        }
        return ResponseEntity.ok().body(client);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        List<ClientDto> client = clientService.searchByName(name);
        if (client.isEmpty()){
            return ResponseEntity.badRequest().body("Customer not found");
        }
        return ResponseEntity.ok().body(client);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(clientService.save(clientDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok().body(clientService.update(id, clientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok().body("Client deleted");
    }
}
