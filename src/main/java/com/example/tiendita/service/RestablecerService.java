package com.example.tiendita.service;

import com.example.tiendita.repository.RestablecerRepository;
import org.springframework.stereotype.Service;

@Service
public class RestablecerService {

    private final RestablecerRepository repo;

    public RestablecerService(RestablecerRepository repo) {
        this.repo = repo;
    }
}