package com.example.tiendita.controller;

import com.example.tiendita.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final GeminiService geminiService;

    public ChatController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    /**
     * POST /chat
     * Body: { "sessionId": "user@email.com", "mensaje": "¿Qué tienen de electrónica?" }
     */
    @PostMapping
    public ResponseEntity<?> chat(@RequestBody Map<String, String> body) {
        String sessionId = body.get("sessionId");
        String mensaje   = body.get("mensaje");

        if (mensaje == null || mensaje.isBlank()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "El campo 'mensaje' es requerido."));
        }

        // Si no viene sessionId, usar "anonimo" como fallback
        if (sessionId == null || sessionId.isBlank()) {
            sessionId = "anonimo";
        }

        try {
            String respuesta = geminiService.chat(sessionId, mensaje);
            return ResponseEntity.ok(Map.of("respuesta", respuesta));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al comunicarse con Gemini: " + e.getMessage()));
        }
    }

    /**
     * DELETE /chat/{sessionId}
     * Limpia la memoria de conversación de una sesión.
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> limpiarSesion(@PathVariable String sessionId) {
        geminiService.limpiarSesion(sessionId);
        return ResponseEntity.ok(Map.of("message", "Sesión limpiada correctamente."));
    }
}
