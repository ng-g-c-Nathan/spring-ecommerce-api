package com.example.tiendita.controller;

import com.example.tiendita.DTO.CambiarEstadoProductoRequestDTO;
import com.example.tiendita.DTO.PedidoRequestDTO;
import com.example.tiendita.domain.Pedido;
import com.example.tiendita.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @PostMapping("/hacer-pedido")
    public ResponseEntity<String> hacerPedido(@RequestBody PedidoRequestDTO request) {
        try {
            int resultado = pedidoService.hacerPedido(request);

            if (resultado == -1) {
                return ResponseEntity.status(400).body("Ya existe un pedido con el mismo ID de compra y cliente");
            } else if (resultado == 0) {
                return ResponseEntity.status(404).body("Cliente o compra no encontrada");
            }

            return ResponseEntity.ok("Pedido creado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/cambiar-estado")
    public ResponseEntity<Map<String, Object>> cambiarEstado(@RequestBody CambiarEstadoProductoRequestDTO request) {

        Map<String, Object> response = new HashMap<>();

        try {

            String resultado = pedidoService.cambiarEstadoPedido(
                    request.getEstado(),
                    request.getID_Pedido()
            );

            if ("ok".equals(resultado)) {
                response.put("success", true);
                response.put("message", "Estado actualizado correctamente");
                return ResponseEntity.ok(response);
            }

            response.put("success", false);
            response.put("message", resultado);
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {

            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }
}