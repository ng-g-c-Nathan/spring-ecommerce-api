package com.example.tiendita.controller;

import com.example.tiendita.DTO.ProveedorDTO;
import com.example.tiendita.service.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("/consultar")
    public ResponseEntity<?> consultarProveedores() {
        List<ProveedorDTO> proveedores = proveedorService.consultarProveedores();
        if (proveedores.isEmpty()) {
            return ResponseEntity.ok("{\"success\":0}");
        }
        return ResponseEntity.ok(proveedores);
    }
}