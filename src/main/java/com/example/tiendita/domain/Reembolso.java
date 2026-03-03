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

    private LocalDate Fecha_Reembolso;
    private BigDecimal Monto;
    private String Motivo;
    private String Autorizada;
}