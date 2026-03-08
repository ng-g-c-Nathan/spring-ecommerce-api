package com.example.tiendita.DTO;

public class CompraRequest {
    private String email;
    private java.math.BigDecimal total;
    private String correoPaypal;
    private String idPaypal;
    private String clientePaypal;
    private String direccionClientePaypal;

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public java.math.BigDecimal getTotal() { return total; }
    public void setTotal(java.math.BigDecimal total) { this.total = total; }

    public String getCorreoPaypal() { return correoPaypal; }
    public void setCorreoPaypal(String correoPaypal) { this.correoPaypal = correoPaypal; }

    public String getIdPaypal() { return idPaypal; }
    public void setIdPaypal(String idPaypal) { this.idPaypal = idPaypal; }

    public String getClientePaypal() { return clientePaypal; }
    public void setClientePaypal(String clientePaypal) { this.clientePaypal = clientePaypal; }

    public String getDireccionClientePaypal() { return direccionClientePaypal; }
    public void setDireccionClientePaypal(String d) { this.direccionClientePaypal = d; }
}
