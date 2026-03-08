package com.example.tiendita.DTO;

public class ActualizarPersonaRequest {

    private String correo;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String apodo;
    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;
    private String instruccionesExtras;
    private String telefono;
    private String correoNuevo;
    private String rfc;

    public ActualizarPersonaRequest(){

    }

    public ActualizarPersonaRequest(String correo, String nombre, String apellidoP, String apellidoM, String apodo, String calle, String numeroExterior, String numeroInterior, String ciudad, String estado, String codigoPostal, String pais, String instruccionesExtras, String telefono, String correoNuevo,String rfc) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.apodo = apodo;
        this.calle = calle;
        this.numeroExterior = numeroExterior;
        this.numeroInterior = numeroInterior;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.instruccionesExtras = instruccionesExtras;
        this.telefono = telefono;
        this.correoNuevo = correoNuevo;
        this.rfc=rfc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoNuevo() {
        return correoNuevo;
    }

    public void setCorreoNuevo(String correoNuevo) {
        this.correoNuevo = correoNuevo;
    }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
}
