package com.example.tiendita.controller;

import com.example.tiendita.service.FacturacionService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/facturacion")
public class FacturacionController {

    private final FacturacionService service;

    public FacturacionController(FacturacionService service) {
        this.service = service;
    }
}