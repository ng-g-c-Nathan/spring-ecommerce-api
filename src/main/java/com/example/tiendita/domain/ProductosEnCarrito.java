package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "productosencarrito")
public class ProductosEnCarrito {

    @EmbeddedId
    private ProductosEnCarritoId id;

    @ManyToOne
    @MapsId("carritoId")
    @JoinColumn(name = "ID_Carrito")
    private Carrito carrito;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "ID_Producto")
    private Producto producto;

    private Integer Cantidad;
}