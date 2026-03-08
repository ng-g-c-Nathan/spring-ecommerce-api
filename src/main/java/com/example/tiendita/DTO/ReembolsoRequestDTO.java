package com.example.tiendita.DTO;

public class ReembolsoRequestDTO {
    private Long idProducto;
    private Long idTransaccion;
    private Long idCompra;
    private Double monto;
    private String razon;
    private String correo;

    public ReembolsoRequestDTO() {}

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public Long getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(Long idTransaccion) { this.idTransaccion = idTransaccion; }

    public Long getIdCompra() { return idCompra; }
    public void setIdCompra(Long idCompra) { this.idCompra = idCompra; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getRazon() { return razon; }
    public void setRazon(String razon) { this.razon = razon; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
