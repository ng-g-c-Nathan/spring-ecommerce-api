package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class ProductosEnListaDeseosId implements Serializable {

    @Column(name = "ID_ListaDeseos")
    private Long listaId;

    @Column(name = "ID_Producto")
    private Long productoId;

    public ProductosEnListaDeseosId(){

    }

    public ProductosEnListaDeseosId(Long listaId, Long productoId) {
        this.listaId = listaId;
        this.productoId = productoId;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}