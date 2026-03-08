package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductosEnCompraId implements Serializable {

    @Column(name = "ID_Compra")
    private Long compraId;

    @Column(name = "ID_Producto")
    private Long productoId;

    public ProductosEnCompraId() {}

    public ProductosEnCompraId(Long compraId, Long productoId) {
        this.compraId = compraId;
        this.productoId = productoId;
    }

    public Long getCompraId() { return compraId; }
    public void setCompraId(Long compraId) { this.compraId = compraId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductosEnCompraId)) return false;
        ProductosEnCompraId that = (ProductosEnCompraId) o;
        return Objects.equals(compraId, that.compraId) &&
                Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compraId, productoId);
    }
}