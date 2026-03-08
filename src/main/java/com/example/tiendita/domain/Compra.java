package com.example.tiendita.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Compra")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Transaccion")
    private Pago pago;

    @OneToMany(mappedBy = "compra")
    private List<ProductosEnCompra> productosEnCompra;

    public Compra() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public List<ProductosEnCompra> getProductosEnCompra() {
        return productosEnCompra;
    }

    public void setProductosEnCompra(List<ProductosEnCompra> productosEnCompra) {
        this.productosEnCompra = productosEnCompra;
    }
}