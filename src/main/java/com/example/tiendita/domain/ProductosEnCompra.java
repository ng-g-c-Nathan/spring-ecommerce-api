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
    public ProductosEnCompra() {
    }

    public ProductosEnCompraId getId() {
        return id;
    }

    public void setId(ProductosEnCompraId id) {
        this.id = id;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
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