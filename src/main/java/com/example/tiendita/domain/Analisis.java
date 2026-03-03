package com.example.tiendita.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "analisis")
public class Analisis {

    @Id
    @Column(name = "FechaDias")
    private LocalDate fechaDias;

    @Column(name = "VistaDiaHoy")
    private Integer vistaDiaHoy;

    @Column(name = "VistasSemana")
    private Integer vistasSemana;

    @Column(name = "VistasMes")
    private Integer vistasMes;
}