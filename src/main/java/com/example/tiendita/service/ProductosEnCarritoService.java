package com.example.tiendita.service;

import com.example.tiendita.repository.ProductosEnCarritoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductosEnCarritoService {

    private final ProductosEnCarritoRepository productosEnCarritoRepository;

    public ProductosEnCarritoService(ProductosEnCarritoRepository productosEnCarritoRepository) {
        this.productosEnCarritoRepository = productosEnCarritoRepository;
    }
    public String obtenerTopUnoProducto() {

        return productosEnCarritoRepository
                .obtenerNombresOrdenados(PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .orElse(null);
    }
    public long contarProductosEnCarrito() {
        return productosEnCarritoRepository.count();
    }
}