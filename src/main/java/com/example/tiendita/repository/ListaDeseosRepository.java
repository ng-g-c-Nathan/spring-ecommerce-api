package com.example.tiendita.repository;

import com.example.tiendita.domain.Carrito;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.domain.ListaDeseos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {
    Optional<ListaDeseos> findByCliente(Cliente cliente);
}