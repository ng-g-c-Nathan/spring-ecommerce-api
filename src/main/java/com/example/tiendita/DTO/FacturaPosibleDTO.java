package com.example.tiendita.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FacturaPosibleDTO {
    private Long idCompra;
    private LocalDate fecha;
    private BigDecimal total;
    private String posible;

    public FacturaPosibleDTO(Long idCompra, LocalDate fecha, BigDecimal total, String posible) {
        this.idCompra = idCompra;
        this.fecha = fecha;
        this.total = total;
        this.posible = posible;
    }

    // Getters
    public Long getIdCompra() {
        return idCompra;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getPosible() {
        return posible;
    }
}
