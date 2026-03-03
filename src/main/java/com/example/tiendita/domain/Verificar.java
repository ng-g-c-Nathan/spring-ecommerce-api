package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "verificar")
public class Verificar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Verificar")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;
}