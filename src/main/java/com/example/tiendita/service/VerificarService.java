package com.example.tiendita.service;

import com.example.tiendita.repository.VerificarRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificarService {

    private final VerificarRepository repo;

    public VerificarService(VerificarRepository repo) {
        this.repo = repo;
    }
}