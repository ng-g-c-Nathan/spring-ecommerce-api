package com.example.tiendita.controller;

import com.example.tiendita.DTO.ActualizarCantidadRequest;
import com.example.tiendita.DTO.ManejarProductoAlUsuarioRequest;
import com.example.tiendita.DTO.ProductosDelClienteDTO;
import com.example.tiendita.DTO.TransferirProductosRequest;
import com.example.tiendita.service.ListaDeseosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/listadeseos")
public class ListaDeseosController {

    private final ListaDeseosService listaDeseosService;

    public ListaDeseosController(ListaDeseosService listaDeseosService) {
        this.listaDeseosService = listaDeseosService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProducto(@RequestBody ManejarProductoAlUsuarioRequest request) {
        try {
            listaDeseosService.agregarALaLista(request);
            return ResponseEntity.ok("{\"success\":1}");
        } catch (Exception e) {
            return ResponseEntity.ok("{\"success\":0,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/pasarCarro")
    public ResponseEntity<?> pasarCarro(@RequestBody TransferirProductosRequest request) {
        try {
            listaDeseosService.pasarCarro(request);
            return ResponseEntity.ok("{\"success\":1}");
        } catch (Exception e) {
            return ResponseEntity.ok("{\"success\":0,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
    @PostMapping("/miLista")
    public ResponseEntity<?> miLista(@RequestBody String email) {

        try {

            List<ProductosDelClienteDTO> productos =
                    listaDeseosService.miLista(email);

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

    @PostMapping("/borrarMiLista")
    public ResponseEntity<?> borrarMiLista(
            @RequestBody ManejarProductoAlUsuarioRequest request) {

        try {

            listaDeseosService.borrarProductoDeLista(request);

            return ResponseEntity.ok(
                    Map.of("success", 1, "message",
                            "Producto borrado de la lista de deseos exitosamente")
            );

        } catch (Exception e) {

            return ResponseEntity.ok(
                    Map.of("success", 0, "message", e.getMessage())
            );
        }
    }

    @PostMapping("/actualizarLista")
    public ResponseEntity<?> actualizarLista(
            @RequestBody ActualizarCantidadRequest request) {

        try {

            listaDeseosService.actualizarCantidadLista(request);
            return ResponseEntity.ok(Map.of("success", 1));

        } catch (Exception e) {

            return ResponseEntity.ok(
                    Map.of("success", 0, "message", e.getMessage())
            );
        }
    }
}