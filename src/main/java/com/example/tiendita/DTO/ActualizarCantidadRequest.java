package com.example.tiendita.DTO;

public class ActualizarCantidadRequest {
    private String email;
    private Long producto;
    private Integer valorNuevo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }

    public Integer getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(Integer valorNuevo) {
        this.valorNuevo = valorNuevo;
    }
}
