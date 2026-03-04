package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class ProductosEnCarritoId implements Serializable {

    @Column(name = "ID_Carrito")
    private Long carritoId;

    @Column(name = "ID_Producto")
    private Long productoId;

    ProductosEnCarritoId(){}

    public ProductosEnCarritoId(Long carritoId, Long productoId) {
        this.carritoId = carritoId;
        this.productoId = productoId;
    }
    public Long getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(Long carritoId) {
        this.carritoId = carritoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}