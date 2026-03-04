package com.example.tiendita.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DevolucionesDTO {

    private Long idCompra;
    private Long idTransaccion;
    private Long idProducto;
    private Integer cantidad;
    private String nombreProducto;
    private BigDecimal precio;
    private LocalDate fechaPago;
    private String metodoPago;
    private String estadoPedido;

    public DevolucionesDTO() {
        // Constructor vacío para JPA / Jackson
    }

    public DevolucionesDTO(Long idCompra, Long idTransaccion, Long idProducto, Integer cantidad,
                           String nombreProducto, BigDecimal precio, LocalDate fechaPago,
                           String metodoPago, String estadoPedido) {
        this.idCompra = idCompra;
        this.idTransaccion = idTransaccion;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estadoPedido = estadoPedido;
    }

    // Getters y Setters
    public Long getIdCompra() { return idCompra; }
    public void setIdCompra(Long idCompra) { this.idCompra = idCompra; }

    public Long getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(Long idTransaccion) { this.idTransaccion = idTransaccion; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstadoPedido() { return estadoPedido; }
    public void setEstadoPedido(String estadoPedido) { this.estadoPedido = estadoPedido; }
}
