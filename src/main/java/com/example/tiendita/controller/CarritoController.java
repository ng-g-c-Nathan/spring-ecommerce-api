package com.example.tiendita.controller;

import com.example.tiendita.DTO.ActualizarCantidadRequest;
import com.example.tiendita.DTO.ManejarProductoAlUsuarioRequest;
import com.example.tiendita.DTO.ProductosDelClienteDTO;
import com.example.tiendita.DTO.TransferirProductosRequest;
import com.example.tiendita.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProducto(@RequestBody ManejarProductoAlUsuarioRequest request) {
        try {
            carritoService.agregarAlCarrito(request);
            return ResponseEntity.ok("{\"success\":1}");
        } catch (Exception e) {
            return ResponseEntity.ok("{\"success\":0,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/pasarLista")
    public ResponseEntity<?> pasarLista(@RequestBody TransferirProductosRequest request) {
        try {
            carritoService.pasarALista(request);
            return ResponseEntity.ok("{\"success\":1}");
        } catch (Exception e) {
            return ResponseEntity.ok("{\"success\":0,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/miCarrito")
    public ResponseEntity<?> miCarrito(@RequestBody String email) {
        try {
            List<ProductosDelClienteDTO> productos =
                    carritoService.miCarrito(email);

            if (productos.isEmpty()) {
                return ResponseEntity.ok(Map.of("success", 0));
            }

            return ResponseEntity.ok(productos);

        } catch (Exception e) {
            return ResponseEntity.ok(
                    Map.of("success", 0, "message", e.getMessage())
            );
        }
    }

    @PostMapping("/borrarMiCarrito")
    public ResponseEntity<?> borrarMiCarrito(
            @RequestBody ManejarProductoAlUsuarioRequest request) {

        try {

            carritoService.borrarProductoDelCarrito(request);

            return ResponseEntity.ok(
                    Map.of("success", 1, "message",
                            "Producto borrado del carrito exitosamente")
            );

        } catch (Exception e) {

            return ResponseEntity.ok(
                    Map.of("success", 0, "message", e.getMessage())
            );
        }
    }

    @PostMapping("/actualizarCarrito")
    public ResponseEntity<?> actualizarCarrito(
            @RequestBody ActualizarCantidadRequest request) {

        try {

            carritoService.actualizarCantidadCarrito(request);
            return ResponseEntity.ok(Map.of("success", 1));

        } catch (Exception e) {

            return ResponseEntity.ok(
                    Map.of("success", 0, "message", e.getMessage())
            );
        }
    }
}