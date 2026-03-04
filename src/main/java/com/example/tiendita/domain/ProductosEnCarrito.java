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
    @Column(name = "Cantidad")
    private Integer cantidad;

    public ProductosEnCarritoId getId() {
        return id;
    }

    public void setId(ProductosEnCarritoId id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}