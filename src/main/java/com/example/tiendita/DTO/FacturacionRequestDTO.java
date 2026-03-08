package com.example.tiendita.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO que recibe el frontend (camelCase) para crear una factura.
 * Jackson mapea automáticamente camelCase → fields Java.
 */
public class FacturacionRequestDTO {

    // ── Identificadores ───────────────────────────────────────────────────
    private String correo;

    @JsonProperty("ID_Compra")
    private Long idCompra;

    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;

    private String razonSocial;
    private String rfc;
    private String regimenFiscal;
    private String codigoPostalFiscal;
    private String usoCfdi;
    private String correoFacturacion;

    // ── Getters y Setters ─────────────────────────────────────────────────

    public String getCorreo()                      { return correo; }
    public void   setCorreo(String correo)         { this.correo = correo; }

    public Long   getIdCompra()                    { return idCompra; }
    public void   setIdCompra(Long idCompra)       { this.idCompra = idCompra; }

    public String getCalle()                       { return calle; }
    public void   setCalle(String calle)           { this.calle = calle; }

    public String getNumeroExterior()              { return numeroExterior; }
    public void   setNumeroExterior(String s)      { this.numeroExterior = s; }

    public String getNumeroInterior()              { return numeroInterior; }
    public void   setNumeroInterior(String s)      { this.numeroInterior = s; }

    public String getCiudad()                      { return ciudad; }
    public void   setCiudad(String ciudad)         { this.ciudad = ciudad; }

    public String getEstado()                      { return estado; }
    public void   setEstado(String estado)         { this.estado = estado; }

    public String getCodigoPostal()                { return codigoPostal; }
    public void   setCodigoPostal(String s)        { this.codigoPostal = s; }

    public String getPais()                        { return pais; }
    public void   setPais(String pais)             { this.pais = pais; }

    public String getRazonSocial()                 { return razonSocial; }
    public void   setRazonSocial(String s)         { this.razonSocial = s; }

    public String getRfc()                         { return rfc; }
    public void   setRfc(String rfc)               { this.rfc = rfc; }

    public String getRegimenFiscal()               { return regimenFiscal; }
    public void   setRegimenFiscal(String s)       { this.regimenFiscal = s; }

    public String getCodigoPostalFiscal()          { return codigoPostalFiscal; }
    public void   setCodigoPostalFiscal(String s)  { this.codigoPostalFiscal = s; }

    public String getUsoCfdi()                     { return usoCfdi; }
    public void   setUsoCfdi(String s)             { this.usoCfdi = s; }

    public String getCorreoFacturacion()           { return correoFacturacion; }
    public void   setCorreoFacturacion(String s)   { this.correoFacturacion = s; }
}