package com.example.tiendita.repository;

import com.example.tiendita.DTO.ProveedorMasGrande;
import com.example.tiendita.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //Hibernate cuida mucho los AS en relacion a los getters
    @Query("""
        SELECT pr.nombreEmpresa AS nombreEmpresa,
               COUNT(p) AS totalProductos
        FROM Producto p
        JOIN p.proveedor pr
        GROUP BY pr.id, pr.nombreEmpresa
        ORDER BY COUNT(p) DESC
    """)
    List<ProveedorMasGrande> proveedorConMasProductos();
    //Al ser mas de 1 campo lo mejor es hacer una lista de ese objeto jeje

    List<Producto> findByStockGreaterThan(Integer stock);

    @Query("SELECT DISTINCT p.categoria FROM Producto p")
    List<String> findDistinctCategorias();

    @Query("SELECT DISTINCT p.categoria FROM Producto p WHERE p.categoria != :categoria")
    List<String> findDistinctCategoriasExcluyendo(@Param("categoria") String categoria);

    List<Producto> findByCategoria(String categoria);
}