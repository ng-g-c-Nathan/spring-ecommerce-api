package com.example.tiendita.DTO;

public class PedidoRequestDTO {
    private String email;
    private Long ID_Compra;

    // Dirección de envío
    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;
    private String instruccionesExtras;

    public PedidoRequestDTO() {}

    // ── Getters & Setters ─────────────────────────────────────────────────

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getID_Compra() { return ID_Compra; }
    public void setID_Compra(Long ID_Compra) { this.ID_Compra = ID_Compra; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumeroExterior() { return numeroExterior; }
    public void setNumeroExterior(String numeroExterior) { this.numeroExterior = numeroExterior; }

    public String getNumeroInterior() { return numeroInterior; }
    public void setNumeroInterior(String numeroInterior) { this.numeroInterior = numeroInterior; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getInstruccionesExtras() { return instruccionesExtras; }
    public void setInstruccionesExtras(String instruccionesExtras) { this.instruccionesExtras = instruccionesExtras; }
}