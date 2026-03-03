package com.example.tiendita.controller;

import com.example.tiendita.service.RestablecerService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/restablecer")
public class RestablecerController {

    private final RestablecerService service;

    public RestablecerController(RestablecerService service) {
        this.service = service;
    }
}