package com.example.tiendita.controller;

import com.example.tiendita.domain.Pedido;
import com.example.tiendita.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/ver")
    public ResponseEntity<?> revisarPedidos() {

        List<Pedido> pedidos = pedidoService.obtenerTodos();

        if (pedidos.isEmpty()) {
            return ResponseEntity.ok(
                    Map.of("success", 0)
            );
        }

        return ResponseEntity.ok(pedidos);
    }
}