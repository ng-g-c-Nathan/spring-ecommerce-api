package com.example.tiendita.DTO;

public class ProveedorGrandeDTO {
    private Long id;
    private String nombreEmpresa;
    private Long totalProductos;

    public ProveedorGrandeDTO(Long id, String nombreEmpresa, Long totalProductos) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.totalProductos = totalProductos;
    }

    public Long getId() {
        return id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public Long getTotalProductos() {
        return totalProductos;
    }
}
