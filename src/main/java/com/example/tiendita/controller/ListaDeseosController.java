package com.example.tiendita.controller;

import com.example.tiendita.service.ListaDeseosService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/listadeseos")
public class ListaDeseosController {

    private final ListaDeseosService service;

    public ListaDeseosController(ListaDeseosService service) {
        this.service = service;
    }
}