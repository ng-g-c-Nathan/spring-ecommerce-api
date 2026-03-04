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
    @Column(name = "Cantidad")
    private Integer cantidad;

    public ProductosEnListaDeseos() {

    }

    public ProductosEnListaDeseos(ProductosEnListaDeseosId id, ListaDeseos listaDeseos, Producto producto, Integer cantidad) {
        this.id = id;
        this.listaDeseos = listaDeseos;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductosEnListaDeseosId getId() {
        return id;
    }

    public void setId(ProductosEnListaDeseosId id) {
        this.id = id;
    }

    public ListaDeseos getListaDeseos() {
        return listaDeseos;
    }

    public void setListaDeseos(ListaDeseos listaDeseos) {
        this.listaDeseos = listaDeseos;
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