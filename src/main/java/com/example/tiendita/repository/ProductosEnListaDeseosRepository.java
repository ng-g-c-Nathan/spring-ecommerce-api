package com.example.tiendita.repository;

import com.example.tiendita.DTO.ProductosDelClienteDTO;
import com.example.tiendita.domain.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductosEnListaDeseosRepository extends JpaRepository<ProductosEnListaDeseos, Long> {
    @Query("""
        SELECT new com.example.tiendita.DTO.ProductosDelClienteDTO(
            p.id,
            p.nombre,
            p.descripcion,
            p.precio,
            p.categoria,
            p.stock,
            pl.cantidad
        )
        FROM ProductosEnListaDeseos pl
        JOIN pl.producto p
        WHERE pl.listaDeseos = :lista
    """)
    List<ProductosDelClienteDTO> obtenerMiLista(ListaDeseos lista);
    Optional<ProductosEnListaDeseos> findByListaDeseosAndProducto(ListaDeseos listaDeseos, Producto producto);

    @Transactional
    @Modifying
    @Query("""
    delete from ProductosEnListaDeseos pl
    where pl.producto.id = :id
""")
    void deleteByProductoId(Long id);
}