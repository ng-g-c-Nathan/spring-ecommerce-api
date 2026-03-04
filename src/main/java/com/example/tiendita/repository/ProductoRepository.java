package com.example.tiendita.repository;

import com.example.tiendita.DTO.ProductoConVSM;
import com.example.tiendita.DTO.ProductoVSMResponse;
import com.example.tiendita.DTO.ProductosConProveedorDTO;
import com.example.tiendita.DTO.ProveedorMasGrandeDto;
import com.example.tiendita.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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
    List<ProveedorMasGrandeDto> proveedorConMasProductos();
    //Al ser mas de 1 campo lo mejor es hacer una lista de ese objeto jeje

    List<Producto> findByStockGreaterThan(Integer stock);

    @Query("SELECT DISTINCT p.categoria FROM Producto p")
    List<String> findDistinctCategorias();

    @Query("SELECT DISTINCT p.categoria FROM Producto p WHERE p.categoria != :categoria")
    List<String> findDistinctCategoriasExcluyendo(@Param("categoria") String categoria);

    List<Producto> findByCategoria(String categoria);

    @Query("""
        SELECT new com.example.tiendita.DTO.ProductosConProveedorDTO(
            p.id,
            p.nombre,
            p.descripcion,
            p.precio,
            p.categoria,
            pr.nombreEmpresa,
            pr.id,
            p.stock
        )
        FROM Producto p
        JOIN p.proveedor pr
        WHERE p.id = :id
    """)
    Optional<ProductosConProveedorDTO> consultarProductoEspecial(Long id);


    @Query(
            value = """
    SELECT 
        p."ID_Producto" AS "idProducto",
        p."Nombre"      AS "nombre",
        p."Descripcion" AS "descripcion",
        p."Categoria"   AS "categoria",
        COALESCE(string_agg(r."Comentario", ' '), '') AS "comentarios"
    FROM "producto" p
    LEFT JOIN "resena" r 
        ON p."ID_Producto" = r."ID_Producto"
    GROUP BY 
        p."ID_Producto",
        p."Nombre",
        p."Descripcion",
        p."Categoria"
    """,
            nativeQuery = true
    )
    List<ProductoConVSM> obtenerProductosParaVsm();

    @Query(
            value = """
        SELECT 
            p."ID_Producto" AS "idProducto",
            p."Nombre"      AS "nombre",
            p."Descripcion" AS "descripcion",
            p."Categoria"   AS "categoria",
            COALESCE(string_agg(r."Comentario", ' '), '') AS "comentarios"
        FROM "producto" p
        LEFT JOIN "resena" r 
            ON p."ID_Producto" = r."ID_Producto"
        WHERE p."ID_Producto" = :id
        GROUP BY 
            p."ID_Producto",
            p."Nombre",
            p."Descripcion",
            p."Categoria"
        """,
            nativeQuery = true
    )
    ProductoConVSM obtenerProductoPorId(@Param("id") Long id);
}