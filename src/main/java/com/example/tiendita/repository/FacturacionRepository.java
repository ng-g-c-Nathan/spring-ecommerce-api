package com.example.tiendita.repository;

import com.example.tiendita.DTO.FacturaDetalleView;
import com.example.tiendita.DTO.FacturaPosibleDTO;
import com.example.tiendita.domain.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {

    @Query(value = """
        SELECT
            CAST(co."ID_Compra" AS BIGINT) AS idCompra,
            t."Fecha_pago"        AS fecha,
            SUM(pc."Cantidad" * p."Precio") AS total,
            CASE
                WHEN EXISTS (
                    SELECT 1 FROM facturacion f
                    WHERE f."ID_Compra" = co."ID_Compra"
                ) THEN 'NO'
                ELSE 'SI'
            END AS posible
        FROM compra co
        JOIN pago t        ON co."ID_Transaccion" = t."ID_transaccion"
        JOIN productosencompra pc ON pc."ID_Compra" = co."ID_Compra"
        JOIN producto p    ON pc."ID_Producto"  = p."ID_Producto"
        WHERE t."ID_Cliente" = (
            SELECT c."ID_Cliente" FROM cliente c WHERE c."Email" = :email
        )
        GROUP BY co."ID_Compra", t."Fecha_pago"
        """, nativeQuery = true)
    List<FacturaPosibleDTO> findFacturasDisponiblesByEmail(@Param("email") String email);

    boolean existsByCliente_IdAndCompra_Id(Long clienteId, Long compraId);

    @Query(value = """
    SELECT
        f."ID_Facturacion",
        CAST(f."Fecha_Emision" AS VARCHAR)      AS fechaEmision,
        CAST(f."Fecha_Vencimiento" AS VARCHAR)  AS fechaVencimiento,
        co."ID_Compra",
        c."Email",
        CONCAT(c."Calle", ', ', c."Numero_exterior", ', ', c."Numero_interior", ', ',
               c."Ciudad", ', ', c."Estado", ', ', c."Codigo_Postal", ', ', c."Pais") AS direccion,
        CONCAT(c."Nombre", ' ', c."Apellido_Paterno", ' ', c."Apellido_Materno")      AS nombreCompleto,
        c."RFC",
        STRING_AGG(CONCAT(p."Nombre", ' (', pec."Cantidad", ')'), ', ')               AS detalles,
        SUM(pec."Cantidad")                                                            AS totalProductos,
        f."Estatus"                                                                    AS estatus
    FROM facturacion f
    INNER JOIN compra co          ON f."ID_Compra"      = co."ID_Compra"
    INNER JOIN pago t             ON co."ID_Transaccion" = t."ID_transaccion"
    INNER JOIN cliente c          ON t."ID_Cliente"     = c."ID_Cliente"
    INNER JOIN productosencompra pec ON co."ID_Compra"  = pec."ID_Compra"
    INNER JOIN producto p         ON pec."ID_Producto"  = p."ID_Producto"
    GROUP BY f."ID_Facturacion", f."Fecha_Emision", f."Fecha_Vencimiento",
             co."ID_Compra", c."Email", c."Calle", c."Numero_exterior",
             c."Numero_interior", c."Ciudad", c."Estado", c."Codigo_Postal",
             c."Pais", c."Nombre", c."Apellido_Paterno", c."Apellido_Materno", c."RFC",
             f."Estatus"
    """, nativeQuery = true)
    List<FacturaDetalleView> findAllFacturasDetalle();

    @Query(value = """
    SELECT
        f."ID_Facturacion",
        CAST(f."Fecha_Emision" AS VARCHAR)      AS fechaEmision,
        CAST(f."Fecha_Vencimiento" AS VARCHAR)  AS fechaVencimiento,
        co."ID_Compra",
        c."Email",
        CONCAT(f."Calle", ', ', f."Numero_exterior", ', ', f."Numero_interior", ', ',
               f."Ciudad", ', ', f."Estado", ', ', f."Codigo_Postal", ', ', f."Pais") AS direccion,
        CONCAT(c."Nombre", ' ', c."Apellido_Paterno", ' ', c."Apellido_Materno")      AS nombreCompleto,
        c."RFC",
        STRING_AGG(CONCAT(p."Nombre", ' (', pec."Cantidad", ')'), ', ')               AS detalles,
        SUM(pec."Cantidad")                                                            AS totalProductos,
        f."Estatus"                                                                    AS estatus
    FROM facturacion f
    INNER JOIN compra co          ON f."ID_Compra"      = co."ID_Compra"
    INNER JOIN pago t             ON co."ID_Transaccion" = t."ID_transaccion"
    INNER JOIN cliente c          ON t."ID_Cliente"     = c."ID_Cliente"
    INNER JOIN productosencompra pec ON co."ID_Compra"  = pec."ID_Compra"
    INNER JOIN producto p         ON pec."ID_Producto"  = p."ID_Producto"
    WHERE f."ID_Facturacion" = :id
    GROUP BY f."ID_Facturacion", f."Fecha_Emision", f."Fecha_Vencimiento",
             co."ID_Compra", c."Email", f."Calle", f."Numero_exterior",
             f."Numero_interior", f."Ciudad", f."Estado", f."Codigo_Postal",
             f."Pais", c."Nombre", c."Apellido_Paterno", c."Apellido_Materno", c."RFC",
             f."Estatus"
    """, nativeQuery = true)
    List<FacturaDetalleView> findFacturaDetalleById(@Param("id") Long id);

    // ── Actualizar estatus ──
    @Modifying
    @Query(value = """
        UPDATE facturacion SET "Estatus" = :estatus
        WHERE "ID_Facturacion" = :id
    """, nativeQuery = true)
    int updateEstatus(@Param("id") Long id, @Param("estatus") String estatus);
}