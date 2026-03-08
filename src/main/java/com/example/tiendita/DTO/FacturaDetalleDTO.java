package com.example.tiendita.DTO;

/**
 * DTO de salida con el detalle completo de una factura.
 * Incluye dirección + datos fiscales.
 * Los nombres de campo están en camelCase para que Jackson
 * los serialice igual al frontend Angular.
 */
public class FacturaDetalleDTO {

    private final Long   idFacturacion;
    private final String fechaEmision;
    private final String fechaVencimiento;
    private final Long   idCompra;
    private final String email;
    private final String direccion;
    private final String nombreCompleto;
    private final String rfc;
    private final String detalles;
    private final Long   totalProductos;
    private final String estatus;

    // ── Datos fiscales ────────────────────────────────────────────────────
    private String razonSocial;
    private String regimenFiscal;
    private Integer codigoPostalFiscal;
    private String usoCfdi;
    private String correoFacturacion;

    // ── Constructor base (compatible con las queries existentes) ──────────
    public FacturaDetalleDTO(Long idFacturacion, String fechaEmision,
                             String fechaVencimiento, Long idCompra,
                             String email, String direccion,
                             String nombreCompleto, String rfc,
                             String detalles, Long totalProductos,
                             String estatus) {
        this.idFacturacion   = idFacturacion;
        this.fechaEmision    = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.idCompra        = idCompra;
        this.email           = email;
        this.direccion       = direccion;
        this.nombreCompleto  = nombreCompleto;
        this.rfc             = rfc;
        this.detalles        = detalles;
        this.totalProductos  = totalProductos;
        this.estatus         = estatus;
    }

    // ── Getters ───────────────────────────────────────────────────────────

    public Long   getIdFacturacion()      { return idFacturacion; }
    public String getFechaEmision()       { return fechaEmision; }
    public String getFechaVencimiento()   { return fechaVencimiento; }
    public Long   getIdCompra()           { return idCompra; }
    public String getEmail()              { return email; }
    public String getDireccion()          { return direccion; }
    public String getNombreCompleto()     { return nombreCompleto; }
    public String getRfc()                { return rfc; }
    public String getDetalles()           { return detalles; }
    public Long   getTotalProductos()     { return totalProductos; }
    public String getEstatus()            { return estatus; }

    public String  getRazonSocial()           { return razonSocial; }
    public void    setRazonSocial(String s)   { this.razonSocial = s; }

    public String  getRegimenFiscal()         { return regimenFiscal; }
    public void    setRegimenFiscal(String s) { this.regimenFiscal = s; }

    public Integer getCodigoPostalFiscal()            { return codigoPostalFiscal; }
    public void    setCodigoPostalFiscal(Integer n)   { this.codigoPostalFiscal = n; }

    public String  getUsoCfdi()               { return usoCfdi; }
    public void    setUsoCfdi(String s)       { this.usoCfdi = s; }

    public String  getCorreoFacturacion()             { return correoFacturacion; }
    public void    setCorreoFacturacion(String s)     { this.correoFacturacion = s; }
}