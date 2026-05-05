package com.example.tiendita.service;

import com.example.tiendita.domain.Producto;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.output.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GeminiService {

    private final GoogleAiGeminiChatModel chatModel;
    private final ProductoService productoService;
    private final int maxMessages;

    /**
     * Mapa de memorias por sessionId.
     * Cada usuario/pestaña tiene su propio hilo de conversación.
     */
    private final Map<String, ChatMemory> memorias = new ConcurrentHashMap<>();

    public GeminiService(
            GoogleAiGeminiChatModel chatModel,
            ProductoService productoService,
            int memoryMaxMessages
    ) {
        this.chatModel       = chatModel;
        this.productoService = productoService;
        this.maxMessages     = memoryMaxMessages;
    }

    /**
     * Envía un mensaje al modelo con memoria de conversación por sesión.
     *
     * @param sessionId  Identificador único del usuario/sesión (ej: email o UUID del frontend)
     * @param mensaje    Texto del usuario
     * @return           Respuesta del asistente
     */
    public String chat(String sessionId, String mensaje) {

        // 1. Obtener o crear la memoria de esta sesión
        ChatMemory memoria = memorias.computeIfAbsent(sessionId, id ->
                MessageWindowChatMemory.withMaxMessages(maxMessages)
        );

        // 2. Si es el primer mensaje de la sesión, inyectar el system prompt con el catálogo
        boolean esPrimerMensaje = memoria.messages().isEmpty();
        if (esPrimerMensaje) {
            String systemPrompt = construirSystemPrompt();
            memoria.add(SystemMessage.from(systemPrompt));
        }

        // 3. Agregar el mensaje del usuario a la memoria
        memoria.add(UserMessage.from(mensaje));

        // 4. Llamar al modelo con toda la historia
        Response<AiMessage> respuesta = chatModel.generate(memoria.messages());

        // 5. Guardar la respuesta del bot en la memoria
        AiMessage mensajeBot = respuesta.content();
        memoria.add(mensajeBot);

        return mensajeBot.text();
    }

    /**
     * Limpia la memoria de una sesión (útil al cerrar el chat o hacer logout).
     */
    public void limpiarSesion(String sessionId) {
        memorias.remove(sessionId);
    }

    /**
     * Construye el system prompt con el catálogo actual de productos.
     */
    private String construirSystemPrompt() {
        List<Producto> productos = productoService.productosDisponibles();

        StringBuilder catalogo = new StringBuilder();
        for (Producto p : productos) {
            catalogo.append(String.format(
                    "- ID: %d | Nombre: %s | Categoría: %s | Precio: $%.2f | Stock: %d | Descripción: %s%n",
                    p.getId(), p.getNombre(), p.getCategoria(),
                    p.getPrecio(), p.getStock(), p.getDescripcion()
            ));
        }

        return String.format(
                "Eres un asistente de ventas amigable de una tienda en línea llamada \"Tiendita\".\n" +
                        "Tu trabajo es ayudar a los clientes a encontrar productos, resolver dudas y hacer recomendaciones.\n" +
                        "Responde siempre en español, de forma breve, amigable y con emojis ocasionales.\n" +
                        "Si el cliente pregunta por productos, usa únicamente el catálogo de abajo.\n" +
                        "Si no tienes el dato exacto, sé honesto y dilo sin inventar.\n" +
                        "Recuerda el contexto de toda la conversación para dar respuestas coherentes.\n\n" +
                        "=== CATÁLOGO ACTUAL ===\n%s=======================\n",
                catalogo.toString()
        );
    }
}