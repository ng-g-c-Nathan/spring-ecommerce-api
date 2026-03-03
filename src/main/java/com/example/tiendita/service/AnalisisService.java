package com.example.tiendita.service;

import com.example.tiendita.repository.AnalisisRepository;
import org.springframework.stereotype.Service;

@Service
public class AnalisisService {

    private final AnalisisRepository repo;

    public AnalisisService(AnalisisRepository repo) {
        this.repo = repo;
    }
}