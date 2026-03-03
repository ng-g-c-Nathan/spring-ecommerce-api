package com.example.tiendita.controller;

import com.example.tiendita.service.ReembolsoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/reembolsos")
public class ReembolsoController {

    private final ReembolsoService service;

    public ReembolsoController(ReembolsoService service) {
        this.service = service;
    }
}