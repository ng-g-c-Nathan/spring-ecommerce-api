package com.example.tiendita.DTO;

public class ResenaRequest {
    private String correo;
    private String comentario;
    private Short calificacion;
    private Long producto;

    public ResenaRequest() {
    }

    public ResenaRequest(String correo, String comentario, Short calificacion, Long producto) {
        this.correo = correo;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.producto = producto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Short getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Short calificacion) {
        this.calificacion = calificacion;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }
}
