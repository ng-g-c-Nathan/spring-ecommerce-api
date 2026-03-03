package com.example.tiendita.service;

import com.example.tiendita.repository.FacturacionRepository;
import org.springframework.stereotype.Service;

@Service
public class FacturacionService {

    private final FacturacionRepository repo;

    public FacturacionService(FacturacionRepository repo) {
        this.repo = repo;
    }
}