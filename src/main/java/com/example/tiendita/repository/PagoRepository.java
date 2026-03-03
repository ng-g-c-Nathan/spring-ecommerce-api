package com.example.tiendita.repository;

import com.example.tiendita.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

}