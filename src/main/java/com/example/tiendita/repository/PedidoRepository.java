package com.example.tiendita.repository;

import com.example.tiendita.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByCompra_IdAndEstadoNot(Long compraId, String estado);
    boolean existsByCompra_IdAndEstado(Long idCompra, String estado);

    boolean existsByCompra_IdAndCliente_Id(Long compraId, Long clienteId);
}