package com.example.tiendita.controller;

import com.example.tiendita.service.ProductosEnListaDeseosService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/productosenlistadeseos")
public class ProductosEnListaDeseosController {

    private final ProductosEnListaDeseosService service;

    public ProductosEnListaDeseosController(ProductosEnListaDeseosService service) {
        this.service = service;
    }
}