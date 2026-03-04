package com.example.tiendita.controller;

import com.example.tiendita.DTO.ProductoConVSM;
import com.example.tiendita.DTO.ProductoVSMResponse;
import com.example.tiendita.service.VsmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/busqueda")
public class VsmController {
    private final VsmService vsmService;

    public VsmController(VsmService vsmService) {
        this.vsmService = vsmService;
    }

    @GetMapping("/vsm")
    public List<ProductoVSMResponse> buscarVsm(@RequestParam("q") String q) {
        return vsmService.buscarVsm(q);
    }

    @GetMapping("/relacionados")
    public List<ProductoVSMResponse> obtenerRelacionados(@RequestParam("id") Long idProducto) {
        return vsmService.obtenerRelacionados(idProducto);
    }
}
