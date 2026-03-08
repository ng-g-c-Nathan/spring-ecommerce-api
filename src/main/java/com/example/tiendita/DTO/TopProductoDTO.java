package com.example.tiendita.DTO;

import java.math.BigDecimal;

public interface TopProductoDTO {
    Long getId();
    String getNombre();
    String getDescripcion();
    BigDecimal getPrecio();
    String getCategoria();
    Integer getStock();
    Integer getTotalVendido();
}
