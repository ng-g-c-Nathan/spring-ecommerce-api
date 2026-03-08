package com.example.tiendita.controller;

import com.example.tiendita.DTO.CompraRequest;
import com.example.tiendita.DTO.DevolucionesDTO;
import com.example.tiendita.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarCompra(@RequestBody CompraRequest request) {
        try {
            Long idCompra = compraService.procesarCompra(request);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Compra procesada exitosamente",
                    "idCompra", idCompra
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}