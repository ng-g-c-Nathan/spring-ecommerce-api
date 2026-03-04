package com.example.tiendita.DTO;

import java.math.BigDecimal;

public class ProductosConProveedorDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private String nombreEmpresa;
    private Long idProveedor;
    private Integer stock;

    public ProductosConProveedorDTO(Long idProducto, String nombre, String descripcion, BigDecimal precio, String categoria,String nombreEmpresa, Long idProveedor, Integer stock) {
        this.id = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.nombreEmpresa = nombreEmpresa;
        this.idProveedor = idProveedor;
        this.stock = stock;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
