package com.example.tiendita.controller;

import com.example.tiendita.service.ProductosEnCarritoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/productosencarrito")
public class ProductosEnCarritoController {

    private final ProductosEnCarritoService service;

    public ProductosEnCarritoController(ProductosEnCarritoService service) {
        this.service = service;
    }
}