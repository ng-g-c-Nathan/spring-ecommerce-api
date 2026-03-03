package com.example.tiendita.service;

import com.example.tiendita.repository.ProductosEnCarritoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductosEnCarritoService {

    private final ProductosEnCarritoRepository repo;

    public ProductosEnCarritoService(ProductosEnCarritoRepository repo) {
        this.repo = repo;
    }
}