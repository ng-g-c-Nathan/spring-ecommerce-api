package com.example.tiendita.repository;

import com.example.tiendita.DTO.DevolucionPosibleDTO;
import com.example.tiendita.domain.Reembolso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReembolsoRepository extends JpaRepository<Reembolso, Long> {

    @Query("""
        SELECT new com.example.tiendita.DTO.DevolucionPosibleDTO(
            co.id,
            t.fechaPago,
            SUM(pc.cantidad * p.precio),
            CASE WHEN EXISTS (
                SELECT 1 FROM Pedido pe WHERE pe.compra.id = co.id
            ) THEN 'NO' ELSE 'SI' END
        )
        FROM Compra co
        JOIN co.pago t
        JOIN co.productosEnCompra pc
        JOIN pc.producto p
        WHERE t.cliente.email = :email
        GROUP BY co.id, t.fechaPago
    """)
    List<DevolucionPosibleDTO> findDevolucionesByEmail(@Param("email") String email);

    boolean existsByPago_IdAndCompra_IdAndProducto_Id(
            Long idTransaccion,
            Long idCompra,
            Long idProducto
    );

    @Query("SELECT COALESCE(MAX(r.id), 0) FROM Reembolso r")
    Long findMaxId();
}