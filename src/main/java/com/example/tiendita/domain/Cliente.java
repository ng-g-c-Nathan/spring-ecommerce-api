package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cliente")
    private Long id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido_Paterno")
    private String apellidoPaterno;

    @Column(name = "Apellido_Materno")
    private String apellidoMaterno;

    @Column(name = "Apodo")
    private String apodo;

    @Column(name = "Permisos")
    private String permisos;

    @Column(name = "Email")
    private String email;

    @Column(name = "Verificacion")
    private String verificacion;

    @Column(name = "Contrasena")
    private String contrasena;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "RFC")
    private String rfc;

    @Column(name = "Calle")
    private String calle;

    @Column(name = "Numero_exterior")
    private String numeroExterior;

    @Column(name = "Numero_interior")
    private String numeroInterior;

    @Column(name = "Ciudad")
    private String ciudad;

    @Column(name = "Estado")
    private String estado;

    @Column(name = "Codigo_Postal")
    private String codigoPostal;

    @Column(name = "Pais")
    private String pais;

    @Column(name = "InstruccionesExtras")
    private String instruccionesExtras;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(String verificacion) {
        this.verificacion = verificacion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getInstruccionesExtras() {
        return instruccionesExtras;
    }

    public void setInstruccionesExtras(String instruccionesExtras) {
        this.instruccionesExtras = instruccionesExtras;
    }
}