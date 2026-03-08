package com.example.tiendita.DTO;

import java.math.BigDecimal;

public class ActualizarProductoDTO {
    private Long EditarID;
    private String EditarNombre;
    private String EditarDescripcion;
    private BigDecimal EditarPrecio;
    private String EditarCategoria;
    private Long EditarProveedor;
    private String EditarNombreProveedor;
    private Integer EditarStock;

    // Getters y Setters
    public Long getEditarID() { return EditarID; }
    public void setEditarID(Long EditarID) { this.EditarID = EditarID; }

    public String getEditarNombre() { return EditarNombre; }
    public void setEditarNombre(String EditarNombre) { this.EditarNombre = EditarNombre; }

    public String getEditarDescripcion() { return EditarDescripcion; }
    public void setEditarDescripcion(String EditarDescripcion) { this.EditarDescripcion = EditarDescripcion; }

    public BigDecimal getEditarPrecio() { return EditarPrecio; }
    public void setEditarPrecio(BigDecimal EditarPrecio) { this.EditarPrecio = EditarPrecio; }

    public String getEditarCategoria() { return EditarCategoria; }
    public void setEditarCategoria(String EditarCategoria) { this.EditarCategoria = EditarCategoria; }

    public Long getEditarProveedor() { return EditarProveedor; }
    public void setEditarProveedor(Long EditarProveedor) { this.EditarProveedor = EditarProveedor; }

    public String getEditarNombreProveedor() { return EditarNombreProveedor; }
    public void setEditarNombreProveedor(String EditarNombreProveedor) { this.EditarNombreProveedor = EditarNombreProveedor; }

    public Integer getEditarStock() { return EditarStock; }
    public void setEditarStock(Integer EditarStock) { this.EditarStock = EditarStock; }
}
