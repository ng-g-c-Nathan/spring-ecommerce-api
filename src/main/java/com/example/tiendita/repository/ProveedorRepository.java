package com.example.tiendita.repository;

import com.example.tiendita.DTO.ProveedorDTO;
import com.example.tiendita.domain.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query("SELECT p.id AS id, p.nombreEmpresa AS nombreEmpresa FROM Proveedor p")
    List<ProveedorDTO> findAllProveedores();
}