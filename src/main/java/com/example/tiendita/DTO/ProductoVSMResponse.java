package com.example.tiendita.DTO;

public class ProductoVSMResponse {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String comentarios;
    private double similitud;

    public ProductoVSMResponse() {
    }

    public ProductoVSMResponse(Long idProducto, String nombre, String descripcion, String categoria, String comentarios, double similitud) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.comentarios = comentarios;
        this.similitud = similitud;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public double getSimilitud() {
        return similitud;
    }

    public void setSimilitud(double similitud) {
        this.similitud = similitud;
    }
}
