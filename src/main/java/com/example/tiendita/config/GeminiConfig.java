package com.example.tiendita.config;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model:gemini-2.0-flash}")
    private String model;

    @Value("${gemini.temperature:0.7}")
    private Double temperature;

    @Value("${gemini.max-tokens:1024}")
    private Integer maxTokens;

    @Value("${gemini.memory.max-messages:20}")
    private Integer maxMessages;

    /**
     * Modelo de Gemini configurado con LangChain4j.
     */
    @Bean
    public GoogleAiGeminiChatModel geminiChatModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(model)
                .temperature(temperature)
                .maxOutputTokens(maxTokens)
                .build();
    }

    /**
     * Memoria de conversación por ventana de mensajes.
     * Recuerda los últimos N mensajes del hilo.
     * Se crea un bean base — en el Service se instancian por sessionId.
     */
    @Bean
    public int memoryMaxMessages() {
        return maxMessages;
    }
}
