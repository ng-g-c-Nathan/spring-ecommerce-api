package com.example.tiendita.service;

import com.example.tiendita.domain.Pago;
import com.example.tiendita.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }
    @Transactional(readOnly = true) //Unicamente lectura al ser pagos jeje
    public List<Pago> obtenerPagos() {
        return pagoRepository.findAll();
    }
}