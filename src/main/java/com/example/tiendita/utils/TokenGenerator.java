package com.example.tiendita.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
@Component
public class TokenGenerator {

    // UUID simple
    private static final Random random = new Random();

    // Token Long aleatorio (para IDs como ID_Verificar)
    public static Long generarTokenLong() {
        return Math.abs(random.nextLong() % 1_000_000_000L); // Máx 9 dígitos
    }
}
