package com.example.tiendita.controller;

import com.example.tiendita.DTO.ClienteLoginRequest;
import com.example.tiendita.DTO.ClienteRegisterRequestDTO;
import com.example.tiendita.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarCliente(@RequestBody ClienteRegisterRequestDTO dto) {
        try {
            clienteService.agregarCliente(dto);
            return ResponseEntity.ok().body("{\"success\":1}");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClienteLoginRequest request) {
        try {
            clienteService.login(request.getEmail(), request.getContrasena());
            return ResponseEntity.ok("{\"success\":1}");
        } catch (Exception e) {
            return ResponseEntity.ok("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}