package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "restablecer")
public class Restablecer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Restablecer")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;
}