package com.example.tiendita.DTO;

public class PosibleReembolsoRequestDTO {
    private Long idProducto;
    private Long idTransaccion;
    private Long idCompra;

    public PosibleReembolsoRequestDTO() {}

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public Long getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(Long idTransaccion) { this.idTransaccion = idTransaccion; }

    public Long getIdCompra() { return idCompra; }
    public void setIdCompra(Long idCompra) { this.idCompra = idCompra; }
}
