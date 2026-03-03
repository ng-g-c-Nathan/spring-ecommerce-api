package com.example.tiendita.DTO;

import java.math.BigDecimal;

public class ProductoNuevoRequest {

    private String agregarNombre;
    private String agregarDescripcion;
    private BigDecimal agregarPrecio;
    private String agregarCategoria;
    private Long agregarProveedor;
    private Integer agregarStock;

    public ProductoNuevoRequest() {}

    public String getAgregarNombre() { return agregarNombre; }
    public void setAgregarNombre(String agregarNombre) { this.agregarNombre = agregarNombre; }

    public String getAgregarDescripcion() { return agregarDescripcion; }
    public void setAgregarDescripcion(String agregarDescripcion) { this.agregarDescripcion = agregarDescripcion; }

    public BigDecimal  getAgregarPrecio() { return agregarPrecio; }
    public void setAgregarPrecio(BigDecimal  agregarPrecio) { this.agregarPrecio = agregarPrecio; }

    public String getAgregarCategoria() { return agregarCategoria; }
    public void setAgregarCategoria(String agregarCategoria) { this.agregarCategoria = agregarCategoria; }

    public Long getAgregarProveedor() { return agregarProveedor; }
    public void setAgregarProveedor(Long agregarProveedor) { this.agregarProveedor = agregarProveedor; }

    public Integer getAgregarStock() { return agregarStock; }
    public void setAgregarStock(Integer agregarStock) { this.agregarStock = agregarStock; }
}
