package com.example.tiendita.repository;

import com.example.tiendita.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    @Query("SELECT p.fechaPago FROM Pago p WHERE p.id = :id")
    LocalDate findFechaPagoByIdTransaccion(@Param("id") Long id);
}