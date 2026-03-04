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
    }