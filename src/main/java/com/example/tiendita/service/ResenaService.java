package com.example.tiendita.service;

import com.example.tiendita.repository.ResenaRepository;
import org.springframework.stereotype.Service;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;

    public ResenaService(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }
    public long contarResenas() {
        return resenaRepository.count();
    }
}