package com.example.tiendita.service;

import com.example.tiendita.repository.CarritoRepository;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    private final CarritoRepository repo;

    public CarritoService(CarritoRepository repo) {
        this.repo = repo;
    }
}