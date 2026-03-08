package com.example.tiendita.controller;

import com.example.tiendita.DTO.ActualizarPersonaRequest;
import com.example.tiendita.DTO.ClienteLoginRequestDTO;
import com.example.tiendita.DTO.ClienteRegisterRequest;
import com.example.tiendita.DTO.ClienteResponseDTO;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarCliente(@RequestBody ClienteRegisterRequest dto) {
        try {
            clienteService.agregarCliente(dto);
            return ResponseEntity.ok().body("{\"success\":1}");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClienteLoginRequestDTO request) {
        try {
            clienteService.login(request.getEmail(), request.getContrasena());
            return ResponseEntity.ok("{\"success\":1}");
        } catch (Exception e) {
            return ResponseEntity.ok("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/existe-correo")
    public Map<String, Integer> existeCorreo(@RequestParam("email") String email) {
        Map<String, Integer> response = new HashMap<>();
        boolean existe = clienteService.emailExiste(email);
        response.put("success", existe ? 1 : 0);
        return response;
    }

    @GetMapping("/cliente")
    public ResponseEntity<?> consultarPersona(@RequestParam String correo) {

        return clienteService.buscarPorCorreo(correo)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.ok(Map.of("error", "No existe la persona")));
    }

    @PostMapping("/actualizar-persona")
    public ResponseEntity<?> actualizarPersona(@RequestBody ActualizarPersonaRequest request) {
        try {
            clienteService.actualizarPersona(request);
            return ResponseEntity.ok(Map.of("success", 1));
        } catch (Exception e) {
            return ResponseEntity.ok(
                    Map.of("success", 0, "error", e.getMessage())
            );
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> revisarAdmin(@RequestBody String email) {

        try {

            int result = clienteService.revisarAdmin(email);

            return ResponseEntity.ok(
                    Map.of("success", result)
            );

        } catch (Exception e) {

            return ResponseEntity.ok(
                    Map.of("success", 4, "error", e.getMessage())
            );
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getId(@RequestParam String email) {
        try {
            Long id = clienteService.getIdByEmail(email);
            return ResponseEntity.ok(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(Map.of("success", 0, "message", e.getMessage()));
        }
    }

    @PutMapping("/modificar-password")
    public ResponseEntity<String> modificarPassword(@RequestBody ClienteLoginRequestDTO request) {
        try {
            if (request.getEmail() == null || request.getContrasena() == null ||
                    request.getEmail().isBlank() || request.getContrasena().isBlank()) {
                return ResponseEntity.status(400).body("Faltan datos de contraseña o correo electrónico");
            }

            boolean resultado = clienteService.modificarPassword(request.getEmail(), request.getContrasena());

            if (resultado) {
                return ResponseEntity.ok("Contraseña actualizada correctamente");
            } else {
                return ResponseEntity.status(404).body("No se encontró un cliente con ese correo");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en el proceso: " + e.getMessage());
        }
    }

    @GetMapping("/apodo")
    public ResponseEntity<?> getApodo(@RequestParam String email) {
        Cliente cliente = clienteService.buscarPorEmail(email);

        if (cliente == null) {
            return ResponseEntity.status(404).body("No se encontró ningún cliente con el email proporcionado");
        }

        String apodoONombre;

        if (cliente.getApodo() != null && !cliente.getApodo().isEmpty()) {
            apodoONombre = cliente.getApodo();
        } else if (cliente.getNombre() != null && !cliente.getNombre().isEmpty()) {
            apodoONombre = cliente.getNombre();
        } else {
            apodoONombre = cliente.getEmail();
        }

        //Retorna ID, correo y apodo
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", cliente.getId());
        respuesta.put("correo", cliente.getEmail());
        respuesta.put("apodo", apodoONombre);
        respuesta.put("permisos", cliente.getPermisos());
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/eliminar-token-reset")
    public ResponseEntity<?> eliminarTokenReset(@RequestParam Long token) {
        try {

            Long idCliente = clienteService.eliminarTokenResetPassword(token);

            return ResponseEntity.ok(Map.of(
                    "success", 1,
                    "ID_Cliente", idCliente
            ));

        } catch (RuntimeException e) {
            // Token no existe
            return ResponseEntity.status(404)
                    .body(Map.of("success", 0, "error", e.getMessage()));

        } catch (Exception e) {
            // Error inesperado
            return ResponseEntity.status(500)
                    .body(Map.of("success", 0, "error", e.getMessage()));
        }
    }

    @GetMapping("/completo")
    public ResponseEntity<?> getClienteCompleto(@RequestParam String email) {
        try {
            ClienteResponseDTO dto = clienteService.getClienteCompleto(email);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/demo/reset-password")
    public ResponseEntity<?> resetPasswordDemo(@RequestParam String email) {

        boolean ok = clienteService.resetPasswordDemo(email);

        if (!ok) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        return ResponseEntity.ok("Password reseteado a 12345678");
    }
}