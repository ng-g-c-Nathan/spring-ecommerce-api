package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Producto")
    private Long id;

    private String Nombre;
    private String Descripcion;
    private BigDecimal Precio;
    private String Categoria;
    private Integer Stock;

    @ManyToOne
    @JoinColumn(name = "ID_Proveedor")
    private Proveedor proveedor;
}