package com.example.tiendita.controller;

import com.example.tiendita.DTO.ProveedorDTO;
import com.example.tiendita.domain.Proveedor;
import com.example.tiendita.service.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("/consultar")
    public ResponseEntity<?> consultarNombresProveedores() {
        List<ProveedorDTO> proveedores = proveedorService.consultarNombresProveedores();
        if (proveedores.isEmpty()) {
            return ResponseEntity.ok("{\"success\":0}");
        }
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/consultarProveedores")
    public ResponseEntity<?> consultarProveedores() {

        List<Proveedor> proveedores =
                proveedorService.obtenerProveedores();

        if (proveedores.isEmpty()) {
            return ResponseEntity.ok(Map.of("success", 0));
        }

        return ResponseEntity.ok(proveedores);
    }

    @PostMapping("/consultarExcepto")
    public ResponseEntity<?> consultarProveedoresExcepto(@RequestBody Long id) {

        return ResponseEntity.ok(
                proveedorService.obtenerProveedoresExcepto(id)
        );
    }

}