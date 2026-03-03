package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "productosenlistadeseos")
public class ProductosEnListaDeseos {

    @EmbeddedId
    private ProductosEnListaDeseosId id;

    @ManyToOne
    @MapsId("listaId")
    @JoinColumn(name = "ID_ListaDeseos")
    private ListaDeseos listaDeseos;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "ID_Producto")
    private Producto producto;

    private Integer Cantidad;
}