package com.example.tiendita.service;

import com.example.tiendita.repository.ProductosEnListaDeseosRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductosEnListaDeseosService {

    private final ProductosEnListaDeseosRepository repo;

    public ProductosEnListaDeseosService(ProductosEnListaDeseosRepository repo) {
        this.repo = repo;
    }
}