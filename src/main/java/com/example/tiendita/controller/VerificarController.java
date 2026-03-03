package com.example.tiendita.controller;

import com.example.tiendita.service.VerificarService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/verificar")
public class VerificarController {

    private final VerificarService service;

    public VerificarController(VerificarService service) {
        this.service = service;
    }
}