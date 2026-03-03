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

    public LocalDate getFechaDias() {
        return fechaDias;
    }

    public void setFechaDias(LocalDate fechaDias) {
        this.fechaDias = fechaDias;
    }

    public Integer getVistaDiaHoy() {
        return vistaDiaHoy;
    }

    public void setVistaDiaHoy(Integer vistaDiaHoy) {
        this.vistaDiaHoy = vistaDiaHoy;
    }

    public Integer getVistasSemana() {
        return vistasSemana;
    }

    public void setVistasSemana(Integer vistasSemana) {
        this.vistasSemana = vistasSemana;
    }

    public Integer getVistasMes() {
        return vistasMes;
    }

    public void setVistasMes(Integer vistasMes) {
        this.vistasMes = vistasMes;
    }
}