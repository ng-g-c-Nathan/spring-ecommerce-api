package com.example.tiendita.repository;

import com.example.tiendita.domain.ProductosEnCarrito;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductosEnCarritoRepository extends JpaRepository<ProductosEnCarrito, Long> {
    @Query("""
        select p.nombre
        from ProductosEnCarrito pc
        join pc.producto p
        order by pc.Cantidad desc
    """)
    List<String> obtenerNombresOrdenados(Pageable pageable);
}