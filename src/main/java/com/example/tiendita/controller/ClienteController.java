package com.example.tiendita.controller;

import com.example.tiendita.DTO.ActualizarPersonaRequest;
import com.example.tiendita.DTO.ClienteLoginRequestDTO;
import com.example.tiendita.DTO.ClienteRegisterRequest;
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
    public Map<String,Integer> existeCorreo(@RequestParam("email") String email){
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
}