package com.example.tiendita.controller;

import com.example.tiendita.DTO.DevolucionesDTO;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.repository.ClienteRepository;
import com.example.tiendita.repository.ProductosEnCompraRepository;
import com.example.tiendita.repository.ProductosEnListaDeseosRepository;
import com.example.tiendita.service.ProductosEnCompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/productosencompra")
public class ProductosEnCompraController {

    private final ProductosEnCompraService productosEnCompraService;
    private final ProductosEnCompraRepository productosEnCompraRepository;
    private final ClienteRepository clienteRepository;

    public ProductosEnCompraController(ProductosEnCompraService productosEnCompraService,ClienteRepository clienteRepository,ProductosEnCompraRepository productosEnCompraRepository) {
        this.productosEnCompraService = productosEnCompraService;
        this.clienteRepository = clienteRepository;
        this.productosEnCompraRepository=productosEnCompraRepository;
    }

    @PostMapping("/devoluciones")
    public ResponseEntity<List<DevolucionesDTO>> obtenerDevoluciones(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        Long idCliente = clienteOpt.get().getId();
        List<DevolucionesDTO> devoluciones = productosEnCompraRepository.findDevolucionesByClienteId(idCliente);
        return ResponseEntity.ok(devoluciones);
    }
}