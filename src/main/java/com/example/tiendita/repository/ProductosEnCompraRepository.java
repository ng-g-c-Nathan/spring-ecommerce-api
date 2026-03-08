package com.example.tiendita.repository;

import com.example.tiendita.DTO.DevolucionesDTO;
import com.example.tiendita.domain.ProductosEnCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductosEnCompraRepository extends JpaRepository<ProductosEnCompra, Long> {

        @Query("""
        SELECT new com.example.tiendita.DTO.DevolucionesDTO(
            co.id,
            co.pago.id,
            pc.producto.id,
            pc.cantidad,
            pc.producto.nombre,
            pc.producto.precio,
            co.pago.fechaPago,
            co.pago.formaPago,
            COALESCE(pe.estado, 'Pedido no realizado')
        )
        FROM ProductosEnCompra pc
        JOIN pc.compra co
        JOIN pc.producto p
        JOIN co.pago t
        LEFT JOIN Pedido pe ON pe.compra.id = co.id
        WHERE t.cliente.id = :idCliente
    """)
        List<DevolucionesDTO> findDevolucionesByClienteId(@Param("idCliente") Long idCliente);

    @Query(value = """
    SELECT COUNT(*) > 0
    FROM productosencompra pc
    JOIN compra co ON pc."ID_Compra" = co."ID_Compra"
    JOIN pago t    ON co."ID_Transaccion" = t."ID_transaccion"
    WHERE pc."ID_Producto" = :idProducto
      AND t."ID_Cliente" = (
          SELECT "ID_Cliente" FROM cliente WHERE "Email" = :email
      )
    """, nativeQuery = true)
    boolean existsCompraByEmailAndProducto(@Param("email") String email,
                                           @Param("idProducto") Long idProducto);
    }