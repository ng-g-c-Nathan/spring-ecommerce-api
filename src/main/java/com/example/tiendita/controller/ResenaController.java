package com.example.tiendita.controller;

import com.example.tiendita.DTO.ResenaRequest;
import com.example.tiendita.service.ResenaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService service) {
        this.resenaService = service;
    }

    @GetMapping("/count")
    public long contarProductosEnLista() {
        return resenaService.contarResenas();
    }

    @GetMapping("/ver")
    public ResponseEntity<?> revisarResena(@RequestParam Long idProducto) {

        var lista = resenaService.obtenerResenasProducto(idProducto);

        if (lista.isEmpty()) {
            return ResponseEntity.ok(Map.of("success", 0));
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/meter-resena")
    public ResponseEntity<?> meterResena(@RequestBody ResenaRequest request) {

        try {
            resenaService.meterResena(request);
            return ResponseEntity.ok(Map.of("success", 1));
        } catch (Exception e) {
            return ResponseEntity.ok(
                    Map.of("success", 0, "error", e.getMessage())
            );
        }
    }
}