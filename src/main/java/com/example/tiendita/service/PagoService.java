package com.example.tiendita.service;

import com.example.tiendita.repository.PagoRepository;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    private final PagoRepository repo;

    public PagoService(PagoRepository repo) {
        this.repo = repo;
    }
}