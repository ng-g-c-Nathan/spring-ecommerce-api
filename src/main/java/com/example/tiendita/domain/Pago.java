package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_transaccion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;

    private String Forma_pago;
    private LocalDate Fecha_pago;
    private BigDecimal Total;
    private String ID_Paypal;
    private String Correo_electronico_paypal;
    private String Nombre_cliente_Paypal;
    private String Direccion_cliente_Paypal;
}