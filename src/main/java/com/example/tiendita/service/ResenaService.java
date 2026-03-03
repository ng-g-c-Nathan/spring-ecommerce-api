package com.example.tiendita.service;

import com.example.tiendita.repository.ResenaRepository;
import org.springframework.stereotype.Service;

@Service
public class ResenaService {

    private final ResenaRepository repo;

    public ResenaService(ResenaRepository repo) {
        this.repo = repo;
    }
}