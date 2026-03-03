package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "listadeseos")
public class ListaDeseos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ListaDeseos")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_Cliente", unique = true)
    private Cliente cliente;
}