package com.example.tiendita.controller;

import com.example.tiendita.DTO.DevolucionPosibleDTO;
import com.example.tiendita.DTO.PosibleReembolsoRequestDTO;
import com.example.tiendita.DTO.ReembolsoRequestDTO;
import com.example.tiendita.domain.Reembolso;
import com.example.tiendita.service.ReembolsoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reembolsos")
public class ReembolsoController {

    private final ReembolsoService reembolsoService;

    public ReembolsoController(ReembolsoService reembolsoService) {
        this.reembolsoService = reembolsoService;
    }

    @GetMapping
    public ResponseEntity<?> getDevolucionesUnicas(@RequestParam String email) {
        List<DevolucionPosibleDTO> devoluciones = reembolsoService.getDevolucionesByEmail(email);

        if (devoluciones.isEmpty()) {
            return ResponseEntity.ok().body(java.util.Map.of("success", 0));
        }

        return ResponseEntity.ok(devoluciones);
    }

    @PostMapping("/existe-reembolso")
    public ResponseEntity<Integer> existeReembolso(@RequestBody PosibleReembolsoRequestDTO request) {
        if (request.getIdProducto() == null ||
                request.getIdTransaccion() == null ||
                request.getIdCompra() == null) {
            return ResponseEntity.status(400).body(0);
        }

        boolean existe = reembolsoService.existeReembolso(
                request.getIdTransaccion(),
                request.getIdCompra(),
                request.getIdProducto()
        );

        return ResponseEntity.ok(existe ? 0 : 1);
    }

    @PostMapping("/hacer-reembolso")
    public ResponseEntity<Map<String, Object>> hacerReembolso(@RequestBody ReembolsoRequestDTO request) {

        Map<String, Object> response = new HashMap<>();

        try {

            Long resultado = reembolsoService.hacerReembolso(request);

            if (resultado == -1L) {
                response.put("success", 0);
                response.put("error", "El pedido debe estar entregado/Completado");
                return ResponseEntity.badRequest().body(response);
            }

            if (resultado == 0L) {
                response.put("success", 0);
                response.put("error", "No se puede procesar el reembolso");
                return ResponseEntity.badRequest().body(response);
            }

            response.put("success", resultado);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("success", 0);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/autorizar/{id}")
    public ResponseEntity<Integer> autorizarDevolucion(@PathVariable Long id) {
        try {
            boolean resultado = reembolsoService.autorizarDevolucion(id);
            return ResponseEntity.ok(resultado ? 1 : 0);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(0);
        }
    }

    @DeleteMapping("/denegar/{id}")
    public ResponseEntity<String> denegarDevolucion(@PathVariable Long id) {
        try {
            int resultado = reembolsoService.denegarDevolucion(id);

            if (resultado == -1) {
                return ResponseEntity.status(400).body("No se puede denegar, el reembolso ya fue autorizado");
            } else if (resultado == 0) {
                return ResponseEntity.status(404).body("Reembolso no encontrado");
            }

            return ResponseEntity.ok("Reembolso denegado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reembolso>> getAllReembolsos() {
        List<Reembolso> lista = reembolsoService.getAllReembolsos();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }
}