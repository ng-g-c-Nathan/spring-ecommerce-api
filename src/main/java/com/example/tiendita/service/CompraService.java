package com.example.tiendita.service;

import com.example.tiendita.repository.CompraRepository;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    private final CompraRepository repo;

    public CompraService(CompraRepository repo) {
        this.repo = repo;
    }
}