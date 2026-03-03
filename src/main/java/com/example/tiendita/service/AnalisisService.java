package com.example.tiendita.service;

import com.example.tiendita.domain.Analisis;
import com.example.tiendita.repository.AnalisisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
@Service
public class AnalisisService {

    private final AnalisisRepository analisisRepository;

    public AnalisisService(AnalisisRepository analisisRepository) {
        this.analisisRepository = analisisRepository;
    }

    @Transactional
    public void registrarNuevaVista() {

        Analisis analisis = analisisRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe registro en analisis"));

        LocalDate hoy = LocalDate.now();
        LocalDate ultimaFecha = analisis.getFechaDias();

        long diferenciaDias = 0;

        if (ultimaFecha != null) {
            diferenciaDias = ChronoUnit.DAYS.between(ultimaFecha, hoy);
        }

        // actualizar fecha
        analisis.setFechaDias(hoy);

        // si pasó al menos 1 día
        if (diferenciaDias >= 1) {
            analisis.setVistaDiaHoy(0);
        }

        // si pasó al menos 7 días
        if (diferenciaDias >= 7) {
            analisis.setVistasSemana(0);
        }

        // si cambió de mes
        if (ultimaFecha != null &&
                (ultimaFecha.getMonthValue() != hoy.getMonthValue()
                        || ultimaFecha.getYear() != hoy.getYear())) {

            analisis.setVistasMes(0);
        }

        // incrementar contadores
        analisis.setVistaDiaHoy(analisis.getVistaDiaHoy() + 1);
        analisis.setVistasSemana(analisis.getVistasSemana() + 1);
        analisis.setVistasMes(analisis.getVistasMes() + 1);

        analisisRepository.save(analisis);
    }

    public Integer obtenerVistasHoy() {
        return analisisRepository.findAll()
                .stream()
                .findFirst()
                .map(Analisis::getVistaDiaHoy)
                .orElse(0);
    }

    public Integer obtenerVistasSemana() {
        return analisisRepository.findAll()
                .stream()
                .findFirst()
                .map(Analisis::getVistasSemana)
                .orElse(0);
    }

    public Integer obtenerVistasMes() {
        return analisisRepository.findAll()
                .stream()
                .findFirst()
                .map(Analisis::getVistasMes)
                .orElse(0);
    }
}