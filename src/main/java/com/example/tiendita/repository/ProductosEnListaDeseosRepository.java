package com.example.tiendita.repository;

import com.example.tiendita.domain.ProductosEnListaDeseos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosEnListaDeseosRepository extends JpaRepository<ProductosEnListaDeseos, Long> {
}