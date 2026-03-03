package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cliente")
    private Long id;

    private String Nombre;
    private String Apellido_Paterno;
    private String Apellido_Materno;
    private String Apodo;
    private String Permisos;
    private String Email;
    private String Verificacion;
    private String Contrasena;
    private String Telefono;
    private String RFC;
    private String Calle;
    private String Numero_exterior;
    private String Numero_interior;
    private String Ciudad;
    private String Estado;
    private String Codigo_Postal;
    private String Pais;

    @Column(name = "InstruccionesExtras")
    private String instruccionesExtras;
}