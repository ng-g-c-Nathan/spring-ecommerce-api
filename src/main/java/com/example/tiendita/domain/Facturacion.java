package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "facturacion")
public class Facturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Facturacion")
    private Long id;

    // ── Relaciones ────────────────────────────────────────────────────────
    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_Compra")
    private Compra compra;

    // ── Fechas ────────────────────────────────────────────────────────────
    @Column(name = "Fecha_Emision")
    private LocalDate fechaEmision;

    @Column(name = "Fecha_Vencimiento")
    private LocalDate fechaVencimiento;

    // ── Dirección de facturación ──────────────────────────────────────────
    @Column(name = "Calle")
    private String calle;

    @Column(name = "Numero_exterior")
    private Integer numeroExterior;

    @Column(name = "Numero_interior")
    private Integer numeroInterior;

    @Column(name = "Ciudad")
    private String ciudad;

    @Column(name = "Estado")
    private String estado;

    @Column(name = "Codigo_Postal")
    private Integer codigoPostal;

    @Column(name = "Pais")
    private String pais;

    // ── Datos fiscales ────────────────────────────────────────────────────
    @Column(name = "RFC", length = 13)
    private String rfc;

    @Column(name = "Razon_Social")
    private String razonSocial;

    @Column(name = "Regimen_Fiscal")
    private String regimenFiscal;

    @Column(name = "Codigo_Postal_Fiscal")
    private Integer codigoPostalFiscal;

    @Column(name = "Uso_CFDI")
    private String usoCfdi;

    @Column(name = "Correo_Facturacion")
    private String correoFacturacion;

    // ── Estatus ───────────────────────────────────────────────────────────
    @Column(name = "Estatus")
    private String estatus;

    // ── Getters y Setters ─────────────────────────────────────────────────

    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }

    public Cliente getCliente()                  { return cliente; }
    public void setCliente(Cliente cliente)      { this.cliente = cliente; }

    public Compra getCompra()                    { return compra; }
    public void setCompra(Compra compra)         { this.compra = compra; }

    public LocalDate getFechaEmision()           { return fechaEmision; }
    public void setFechaEmision(LocalDate d)     { this.fechaEmision = d; }

    public LocalDate getFechaVencimiento()       { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate d) { this.fechaVencimiento = d; }

    public String getCalle()                     { return calle; }
    public void setCalle(String calle)           { this.calle = calle; }

    public Integer getNumeroExterior()           { return numeroExterior; }
    public void setNumeroExterior(Integer n)     { this.numeroExterior = n; }

    public Integer getNumeroInterior()           { return numeroInterior; }
    public void setNumeroInterior(Integer n)     { this.numeroInterior = n; }

    public String getCiudad()                    { return ciudad; }
    public void setCiudad(String ciudad)         { this.ciudad = ciudad; }

    public String getEstado()                    { return estado; }
    public void setEstado(String estado)         { this.estado = estado; }

    public Integer getCodigoPostal()             { return codigoPostal; }
    public void setCodigoPostal(Integer n)       { this.codigoPostal = n; }

    public String getPais()                      { return pais; }
    public void setPais(String pais)             { this.pais = pais; }

    public String getRfc()                       { return rfc; }
    public void setRfc(String rfc)               { this.rfc = rfc; }

    public String getRazonSocial()               { return razonSocial; }
    public void setRazonSocial(String s)         { this.razonSocial = s; }

    public String getRegimenFiscal()             { return regimenFiscal; }
    public void setRegimenFiscal(String s)       { this.regimenFiscal = s; }

    public Integer getCodigoPostalFiscal()       { return codigoPostalFiscal; }
    public void setCodigoPostalFiscal(Integer n) { this.codigoPostalFiscal = n; }

    public String getUsoCfdi()                   { return usoCfdi; }
    public void setUsoCfdi(String s)             { this.usoCfdi = s; }

    public String getCorreoFacturacion()         { return correoFacturacion; }
    public void setCorreoFacturacion(String s)   { this.correoFacturacion = s; }

    public String getEstatus()                   { return estatus; }
    public void setEstatus(String estatus)       { this.estatus = estatus; }
}