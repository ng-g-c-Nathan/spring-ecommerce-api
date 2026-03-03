package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_transaccion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;

    @Column(name = "Forma_pago")
    private String formaPago;

    @Column(name = "Fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "Total")
    private BigDecimal total;

    @Column(name = "ID_Paypal")
    private String idPaypal;

    @Column(name = "Correo_electronico_paypal")
    private String correoElectronicoPaypal;

    @Column(name = "Nombre_cliente_Paypal")
    private String nombreClientePaypal;

    @Column(name = "Direccion_cliente_Paypal")
    private String direccionClientePaypal;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getIdPaypal() {
        return idPaypal;
    }

    public void setIdPaypal(String idPaypal) {
        this.idPaypal = idPaypal;
    }

    public String getCorreoElectronicoPaypal() {
        return correoElectronicoPaypal;
    }

    public void setCorreoElectronicoPaypal(String correoElectronicoPaypal) {
        this.correoElectronicoPaypal = correoElectronicoPaypal;
    }

    public String getNombreClientePaypal() {
        return nombreClientePaypal;
    }

    public void setNombreClientePaypal(String nombreClientePaypal) {
        this.nombreClientePaypal = nombreClientePaypal;
    }

    public String getDireccionClientePaypal() {
        return direccionClientePaypal;
    }

    public void setDireccionClientePaypal(String direccionClientePaypal) {
        this.direccionClientePaypal = direccionClientePaypal;
    }
}