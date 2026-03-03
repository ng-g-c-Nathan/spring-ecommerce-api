package com.example.tiendita.service;

import com.example.tiendita.DTO.ProveedorDTO;
import com.example.tiendita.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<ProveedorDTO> consultarProveedores() {
        return proveedorRepository.findAllProveedores();
    }
}