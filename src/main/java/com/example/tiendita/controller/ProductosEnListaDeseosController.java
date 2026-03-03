package com.example.tiendita.controller;

import com.example.tiendita.service.ProductosEnListaDeseosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/listas")
public class ProductosEnListaDeseosController {

    private final ProductosEnListaDeseosService productosEnListaDeseosService;

    public ProductosEnListaDeseosController(ProductosEnListaDeseosService productosEnListaDeseosService) {
        this.productosEnListaDeseosService = productosEnListaDeseosService;
    }

    @GetMapping("/count")
    public long contarProductosEnLista() {
        return productosEnListaDeseosService.contarProductosEnLista();
    }
}