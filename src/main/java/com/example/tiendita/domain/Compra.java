package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Compra")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Transaccion")
    private Pago pago;
}