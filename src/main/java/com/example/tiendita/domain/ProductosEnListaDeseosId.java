package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class ProductosEnListaDeseosId implements Serializable {

    @Column(name = "ID_ListaDeseos")
    private Long listaId;

    @Column(name = "ID_Producto")
    private Long productoId;
}