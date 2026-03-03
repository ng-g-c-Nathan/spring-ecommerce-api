package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(
        name = "resena",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ID_Cliente","ID_Producto"})
)
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Resena")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_Producto")
    private Producto producto;

    private Short Calificacion;
    private String Comentario;
}