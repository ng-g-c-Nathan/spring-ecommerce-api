package com.example.tiendita.service;

import com.example.tiendita.repository.ProductosEnCompraRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductosEnCompraService {

    private final ProductosEnCompraRepository repo;

    public ProductosEnCompraService(ProductosEnCompraRepository repo) {
        this.repo = repo;
    }
}