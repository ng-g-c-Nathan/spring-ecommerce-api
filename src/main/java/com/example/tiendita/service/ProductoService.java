package com.example.tiendita.service;

import com.example.tiendita.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }
}