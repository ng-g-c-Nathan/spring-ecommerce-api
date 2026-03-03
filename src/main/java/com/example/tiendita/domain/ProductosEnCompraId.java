package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class ProductosEnCompraId implements Serializable {

    @Column(name = "ID_Compra")
    private Long compraId;

    @Column(name = "ID_Producto")
    private Long productoId;
}