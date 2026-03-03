package com.example.tiendita.controller;

import com.example.tiendita.service.ResenaService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/resenas")
public class ResenaController {

    private final ResenaService service;

    public ResenaController(ResenaService service) {
        this.service = service;
    }
}