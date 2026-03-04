package com.example.tiendita.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Proveedor")
    private Long id;

    @Column(name = "NombreEmpresa")
    private String nombreEmpresa;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "DireccionEmpresa")
    private String direccionEmpresa;

    @Column(name = "CorreoContacto")
    private String correoContacto;

    @Column(name = "TelefonoContacto")
    private Long telefonoContacto;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public Long getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(Long telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }
}