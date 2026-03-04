package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "productosencompra")
public class ProductosEnCompra {

    @EmbeddedId
    private ProductosEnCompraId id;

    @ManyToOne
    @MapsId("compraId")
    @JoinColumn(name = "ID_Compra")
    private Compra compra;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "ID_Producto")
    private Producto producto;
    @Column(name = "Cantidad")
    private Integer cantidad;
}