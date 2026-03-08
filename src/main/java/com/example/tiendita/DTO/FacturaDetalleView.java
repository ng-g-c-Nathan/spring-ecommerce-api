package com.example.tiendita.DTO;

public interface FacturaDetalleView {
    Long getIdFacturacion();
    String getFechaEmision();
    String getFechaVencimiento();
    Long getIdCompra();
    String getEmail();
    String getDireccion();
    String getNombreCompleto();
    String getRfc();
    String getDetalles();
    Long getTotalProductos();
    String getEstatus();
}
