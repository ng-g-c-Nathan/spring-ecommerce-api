package com.example.tiendita.controller;

import com.example.tiendita.service.ProductosEnCarritoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/carritos")
public class ProductosEnCarritoController {

    private final ProductosEnCarritoService productosEnCarritoService;

    public ProductosEnCarritoController(ProductosEnCarritoService service) {
        this.productosEnCarritoService = service;
    }

    @GetMapping("/top")
    public String topUnoCarrito() {
        return productosEnCarritoService.obtenerTopUnoProducto();
    }

    @GetMapping("/count")
    public long cuantosCarritos() {
        return productosEnCarritoService.contarProductosEnCarrito();
    }
}