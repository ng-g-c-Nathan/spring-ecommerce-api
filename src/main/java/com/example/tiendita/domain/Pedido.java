package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Pedido")
    private Long id;

    private LocalDate Fecha_pedido;
    private LocalDate Fecha_entrega;
    private String Estado;

    @ManyToOne
    @JoinColumn(name = "ID_Compra")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;

    private String Calle;
    private Integer Numero_exterior;
    private Integer Numero_interior;
    private String Ciudad;
    private String EstadoOrigen;
    private Integer Codigo_Postal;
    private String Pais;

    @Column(name = "InstruccionesExtras")
    private String instruccionesExtras;
}