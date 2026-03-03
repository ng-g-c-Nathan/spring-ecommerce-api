package com.example.tiendita.controller;

import com.example.tiendita.service.ProductosEnCompraService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/productosencompra")
public class ProductosEnCompraController {

    private final ProductosEnCompraService service;

    public ProductosEnCompraController(ProductosEnCompraService service) {
        this.service = service;
    }
}