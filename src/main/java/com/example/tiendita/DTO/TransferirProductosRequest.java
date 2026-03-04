package com.example.tiendita.DTO;

public class TransferirProductosRequest {

    private String email;
    private Long idProducto;
    private Integer cantidad;

    public TransferirProductosRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
