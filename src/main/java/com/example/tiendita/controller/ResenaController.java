package com.example.tiendita.controller;

import com.example.tiendita.service.ResenaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

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
}