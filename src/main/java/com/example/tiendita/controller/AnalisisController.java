package com.example.tiendita.controller;

import com.example.tiendita.service.AnalisisService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/analisis")
public class AnalisisController {

    private final AnalisisService service;

    public AnalisisController(AnalisisService service) {
        this.service = service;
    }
}