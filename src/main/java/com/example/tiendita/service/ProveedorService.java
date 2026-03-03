package com.example.tiendita.service;

import com.example.tiendita.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService {

    private final ProveedorRepository repo;

    public ProveedorService(ProveedorRepository repo) {
        this.repo = repo;
    }
}