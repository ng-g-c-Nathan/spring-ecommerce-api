package com.example.tiendita.controller;

import com.example.tiendita.domain.Pago;
import com.example.tiendita.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/checar")
    public ResponseEntity<?> obtenerPagos() {

        List<Pago> pagos = pagoService.obtenerPagos();

        if (pagos.isEmpty()) {
            return ResponseEntity.ok(
                    Map.of("success", 0)
            );
        }

        return ResponseEntity.ok(pagos);
    }
}