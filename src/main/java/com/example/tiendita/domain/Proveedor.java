package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Proveedor")
    private Long id;

    private String NombreEmpresa;
    private String DireccionEmpresa;
    private String CorreoContacto;
    private Long TelefonoContacto;
}