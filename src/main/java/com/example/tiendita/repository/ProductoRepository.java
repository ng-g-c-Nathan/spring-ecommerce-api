package com.example.tiendita.repository;

import com.example.tiendita.DTO.*;
import com.example.tiendita.domain.Producto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //Hibernate cuida mucho los AS en relacion a los getters

    //Al ser mas de 1 campo lo mejor es hacer una lista de ese objeto jeje
    List<Producto> findAllByOrderByPrecioAsc();
    List<Producto> findAllByOrderByPrecioDesc();
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
            p."Precio" AS "precio",
        COALESCE(string_agg(r."Comentario", ' '), '') AS "comentarios"
    FROM "producto" p
    LEFT JOIN "resena" r 
        ON p."ID_Producto" = r."ID_Producto"
    GROUP BY 
        p."ID_Producto",
        p."Nombre",
        p."Descripcion",
        p."Categoria",
        p."Precio"
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

   @Query(value = """
    SELECT p."ID_Producto"  AS id,
           p."Nombre"       AS nombre,
           p."Descripcion"  AS descripcion,
           p."Precio"       AS precio,
           p."Categoria"    AS categoria,
           p."Stock"        AS stock,
           s."TotalVendido" AS totalVendido
    FROM producto p
    INNER JOIN (
        SELECT "ID_Producto", SUM("Cantidad") AS "TotalVendido"
        FROM productosencarrito
        GROUP BY "ID_Producto"
        ORDER BY "TotalVendido" DESC
        LIMIT 10
    ) s ON p."ID_Producto" = s."ID_Producto"
    """, nativeQuery = true)
    List<TopProductoDTO> findTopDiez();

    @Query(value = "SELECT * FROM producto ORDER BY RANDOM() LIMIT 4", nativeQuery = true)
    List<Producto> findRandom();

    @Query("""
SELECT new com.example.tiendita.DTO.ProveedorGrandeDTO(
    p.id,
    p.nombreEmpresa,
    COUNT(prod.id)
)
FROM Proveedor p
LEFT JOIN Producto prod ON prod.proveedor.id = p.id
GROUP BY p.id, p.nombreEmpresa
""")
    List<ProveedorGrandeDTO> proveedorConMasProductos();
}