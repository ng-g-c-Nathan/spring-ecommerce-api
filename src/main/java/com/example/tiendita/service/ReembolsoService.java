package com.example.tiendita.service;

import com.example.tiendita.repository.ReembolsoRepository;
import org.springframework.stereotype.Service;

@Service
public class ReembolsoService {

    private final ReembolsoRepository repo;

    public ReembolsoService(ReembolsoRepository repo) {
        this.repo = repo;
    }
}