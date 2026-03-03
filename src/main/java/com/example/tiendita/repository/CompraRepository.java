package com.example.tiendita.repository;

import com.example.tiendita.domain.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}