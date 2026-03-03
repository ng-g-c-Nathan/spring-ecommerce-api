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

    private LocalDate Fecha_Emision;
    private LocalDate Fecha_Vencimiento;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_Compra")
    private Compra compra;

    private String Calle;
    private Integer Numero_exterior;
    private Integer Numero_interior;
    private String Ciudad;
    private String Estado;
    private Integer Codigo_Postal;
    private String Pais;
}