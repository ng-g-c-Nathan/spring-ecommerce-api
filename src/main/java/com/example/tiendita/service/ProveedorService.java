package com.example.tiendita.service;

import com.example.tiendita.DTO.ProveedorDTO;
import com.example.tiendita.domain.Proveedor;
import com.example.tiendita.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<ProveedorDTO> consultarNombresProveedores() {
        return proveedorRepository.findAllProveedores();
    }

    public List<Proveedor> obtenerProveedores() {
        return proveedorRepository.obtenerProveedores();
    }

    public List<Proveedor> obtenerProveedoresExcepto(Long id) {
        return proveedorRepository.obtenerProveedoresExcepto(id);
    }

}