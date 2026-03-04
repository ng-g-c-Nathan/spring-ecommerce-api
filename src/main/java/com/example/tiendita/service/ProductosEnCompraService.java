package com.example.tiendita.service;

import com.example.tiendita.DTO.DevolucionesDTO;
import com.example.tiendita.repository.ClienteRepository;
import com.example.tiendita.repository.ProductosEnCompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductosEnCompraService {

    private final ProductosEnCompraRepository productosEnCompraRepository;
    private final ClienteRepository clienteRepository;

    public ProductosEnCompraService(ProductosEnCompraRepository productosEnCompraRepository,ClienteRepository clienteRepository) {
        this.productosEnCompraRepository = productosEnCompraRepository;
        this.clienteRepository=clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<DevolucionesDTO> getDevolucionesByEmail(String email) {
        return clienteRepository.findByEmail(email)
                .map(cliente -> productosEnCompraRepository.findDevolucionesByClienteId(cliente.getId()))
                .orElse(Collections.emptyList());
    }
}