package com.example.tiendita.repository;

import com.example.tiendita.DTO.ProductosDelClienteDTO;
import com.example.tiendita.domain.Carrito;
import com.example.tiendita.domain.Producto;
import com.example.tiendita.domain.ProductosEnCarrito;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductosEnCarritoRepository extends JpaRepository<ProductosEnCarrito, Long> {
    @Query("""
        select p.nombre
        from ProductosEnCarrito pc
        join pc.producto p
        order by pc.cantidad desc
    """)
    List<String> obtenerNombresOrdenados(Pageable pageable);

    @Query("""
        SELECT new com.example.tiendita.DTO.ProductosDelClienteDTO(
            p.id,
            p.nombre,
            p.descripcion,
            p.precio,
            p.categoria,
            p.stock,
            pc.cantidad
        )
        FROM ProductosEnCarrito pc
        JOIN pc.producto p
        WHERE pc.carrito = :carrito
    """)
    List<ProductosDelClienteDTO> obtenerMiCarrito(Carrito carrito);

    Optional<ProductosEnCarrito> findByCarritoAndProducto(Carrito carrito, Producto producto);

    @Transactional
    @Modifying
    @Query("""
    delete from ProductosEnCarrito pc
    where pc.producto.id = :id
""")
    void deleteByProductoId(Long id);
}