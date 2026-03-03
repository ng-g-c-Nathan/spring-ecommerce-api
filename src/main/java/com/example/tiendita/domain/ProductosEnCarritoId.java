package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class ProductosEnCarritoId implements Serializable {

    @Column(name = "ID_Carrito")
    private Long carritoId;

    @Column(name = "ID_Producto")
    private Long productoId;
}