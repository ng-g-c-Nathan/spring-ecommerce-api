package com.example.tiendita.repository;

import com.example.tiendita.DTO.ResenaDTO;
import com.example.tiendita.domain.Resena;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    @Transactional
    @Modifying
    @Query("""
    delete from Resena r
    where r.producto.id = :id
""")
    void deleteByProductoId(Long id);

    @Query("""
        SELECT 
            c.id as idCliente,
            COALESCE(c.apodo, c.nombre, c.email) as email,
            r.comentario as comentario,
            r.calificacion as calificacion
        FROM Resena r
        JOIN r.cliente c
        WHERE r.producto.id = :idProducto
    """)
    List<ResenaDTO> buscarResenasProducto(Long idProducto);

    boolean existsByCliente_IdAndProducto_Id(Long clienteId, Long productoId);
}