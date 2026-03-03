package com.example.tiendita.controller;

import com.example.tiendita.domain.EmailRequest;
import com.example.tiendita.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> enviar(@RequestBody EmailRequest request) {

        emailService.enviarCorreo(
                request.getTo(),
                request.getSubject(),
                request.getBody()
        );

        return ResponseEntity.ok().build();
    }
}