package com.example.tiendita.DTO;

public class ManejarProductoAlUsuarioRequest {
    private String email;
    private Long idProducto;

    public ManejarProductoAlUsuarioRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
}
