package com.example.tiendita.DTO;

public class CambiarEstadoProductoRequestDTO {
    private String Estado;
    private Long ID_Pedido;

    public CambiarEstadoProductoRequestDTO() {
    }

    public CambiarEstadoProductoRequestDTO(String Estado, Long ID_Pedido) {
        this.Estado = Estado;
        this.ID_Pedido = ID_Pedido;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public Long getID_Pedido() {
        return ID_Pedido;
    }

    public void setID_Pedido(Long ID_Pedido) {
        this.ID_Pedido = ID_Pedido;
    }
}
