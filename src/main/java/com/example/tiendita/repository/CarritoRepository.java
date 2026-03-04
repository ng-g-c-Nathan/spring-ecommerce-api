package com.example.tiendita.repository;

import com.example.tiendita.domain.Carrito;
import com.example.tiendita.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByCliente(Cliente cliente);
}