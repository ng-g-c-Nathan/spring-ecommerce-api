package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Carrito")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_Cliente", unique = true)
    private Cliente cliente;
}