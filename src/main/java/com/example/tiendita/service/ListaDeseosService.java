package com.example.tiendita.service;

import com.example.tiendita.repository.ListaDeseosRepository;
import org.springframework.stereotype.Service;

@Service
public class ListaDeseosService {

    private final ListaDeseosRepository repo;

    public ListaDeseosService(ListaDeseosRepository repo) {
        this.repo = repo;
    }
}