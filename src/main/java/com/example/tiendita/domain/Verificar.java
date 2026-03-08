package com.example.tiendita.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "verificar")
public class Verificar {

    @Id
    @Column(name = "ID_Verificar")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;

    public Verificar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}