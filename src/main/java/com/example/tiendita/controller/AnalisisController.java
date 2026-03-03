package com.example.tiendita.controller;

import com.example.tiendita.service.AnalisisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/analisis")
public class AnalisisController {

    private final AnalisisService analisisService;

    public AnalisisController(AnalisisService service) {
        this.analisisService = service;
    }


    //Registra una nueva vista
    @PostMapping("/nuevo")
    public void nuevo() {
        analisisService.registrarNuevaVista();
    }

    // Regresa cuantas vistas tuvimos en el dia de hoy
    @GetMapping("/hoy-cuantos")
    public Integer hoyCuantos() {
        return analisisService.obtenerVistasHoy();
    }

    // Regresa cuantas vistas tuvimos en la semana
    @GetMapping("/semana-cuantos")
    public Integer semanaCuantos() {
        return analisisService.obtenerVistasSemana();
    }

    // Regresa cuantas vistas tuvimos en el mes
    @GetMapping("/mes-cuantos")
    public Integer mesesCuantos() {
        return analisisService.obtenerVistasMes();
    }
}