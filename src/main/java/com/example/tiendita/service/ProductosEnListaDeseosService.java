package com.example.tiendita.service;

import com.example.tiendita.repository.ProductosEnListaDeseosRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductosEnListaDeseosService {

    private final ProductosEnListaDeseosRepository productosEnListaDeseosRepository;

    public ProductosEnListaDeseosService(ProductosEnListaDeseosRepository productosEnListaDeseosRepository) {
        this.productosEnListaDeseosRepository = productosEnListaDeseosRepository;
    }
    public long contarProductosEnLista() {
        return productosEnListaDeseosRepository.count();
    }
}