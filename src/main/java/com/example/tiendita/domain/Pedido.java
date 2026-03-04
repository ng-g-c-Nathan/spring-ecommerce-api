package com.example.tiendita.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Pedido")
    private Long id;

    @Column(name = "Fecha_pedido")
    private LocalDate fechaPedido;

    @Column(name = "Fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "Estado")
    private String estado;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "ID_Compra")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "Calle")
    private String calle;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "Numero_exterior")
    private Integer numeroExterior;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "Numero_interior")
    private Integer numeroInterior;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "Ciudad")
    private String ciudad;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "EstadoOrigen")
    private String estadoOrigen;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "Codigo_Postal")
    private Integer codigoPostal;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "Pais")
    private String pais;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "InstruccionesExtras")
    private String instruccionesExtras;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(Integer numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public Integer getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(Integer numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(String estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getInstruccionesExtras() {
        return instruccionesExtras;
    }

    public void setInstruccionesExtras(String instruccionesExtras) {
        this.instruccionesExtras = instruccionesExtras;
    }
}