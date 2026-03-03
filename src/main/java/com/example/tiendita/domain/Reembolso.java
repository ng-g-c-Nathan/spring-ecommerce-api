package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reembolso")
public class Reembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Reembolso")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Transaccion")
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "ID_Compra")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "ID_Producto")
    private Producto producto;

    @Column(name = "Fecha_Reembolso")
    private LocalDate fechaReembolso;

    @Column(name = "Monto")
    private BigDecimal monto;

    @Column(name = "Motivo")
    private String motivo;

    @Column(name = "Autorizada")
    private String autorizada;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public LocalDate getFechaReembolso() {
        return fechaReembolso;
    }

    public void setFechaReembolso(LocalDate fechaReembolso) {
        this.fechaReembolso = fechaReembolso;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getAutorizada() {
        return autorizada;
    }

    public void setAutorizada(String autorizada) {
        this.autorizada = autorizada;
    }
}