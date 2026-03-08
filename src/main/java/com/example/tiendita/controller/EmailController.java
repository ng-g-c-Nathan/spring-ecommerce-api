package com.example.tiendita.controller;

import com.example.tiendita.DTO.EmailDevolucionRequest;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.domain.EmailRequest;
import com.example.tiendita.service.ClienteService;
import com.example.tiendita.service.EmailService;
import com.example.tiendita.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mail")
public class EmailController {

    private final EmailService emailService;
    private final ClienteService clienteService;

    @Value("${UrlFrontend}")
    private String urlFrontend;

    public EmailController(EmailService emailService, ClienteService clienteService) {
        this.emailService = emailService;
        this.clienteService = clienteService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> enviar(@RequestBody EmailRequest request) {
        emailService.enviarCorreo(request.getTo(), request.getSubject(), request.getBody());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activar-cuenta")
    public ResponseEntity<String> activarCuenta(@RequestParam String email) {
        try {
            Cliente cliente = clienteService.buscarPorEmail(email);
            if (cliente == null) {
                return ResponseEntity.status(404).body("Cliente no encontrado");
            }

            Long token = TokenGenerator.generarTokenLong();
            clienteService.guardarTokenVerificacion(cliente, token);

            String link = urlFrontend + "/verify/" + token;

            String cuerpo = """
                    Estimado usuario,<br><br>
                    Bienvenido a nuestro servicio. Para activar su cuenta,
                    por favor haga clic en el siguiente enlace:
                    <a href="%s">Activar cuenta</a><br><br>
                    <strong>Nota:</strong> Este enlace es de un solo uso.
                    Una vez abierto, dejará de funcionar.<br><br>
                    Si no solicitó la activación, ignore este correo.<br><br>
                    Atentamente,<br>Su equipo de soporte
                    """.formatted(link);

            emailService.enviarHtml(email, "Activación de cuenta", cuerpo);

            return ResponseEntity.ok("Correo enviado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/verificar-cuenta")
    public ResponseEntity<String> verificarCuenta(@RequestParam Long token) {
        try {
            boolean resultado = clienteService.verificarCuenta(token);
            if (resultado) {
                return ResponseEntity.ok("Cliente verificado correctamente");
            } else {
                return ResponseEntity.status(404).body("No se encontró un cliente asociado al token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en el proceso: " + e.getMessage());
        }
    }


    @PostMapping(value = "/send-adjunto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> enviarConAdjunto(
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam("archivo") MultipartFile archivo) {

        emailService.enviarConAdjunto(to, subject, body, archivo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/solicitar-reset-password")
    public ResponseEntity<String> solicitarResetPassword(@RequestParam String email) {
        try {

            Cliente cliente = clienteService.buscarPorEmail(email);

            if (cliente == null) {
                return ResponseEntity.status(404).body("Cliente no encontrado");
            }

            // generar token
            Long token = TokenGenerator.generarTokenLong();

            // guardar token en BD
            clienteService.guardarTokenResetPassword(cliente, token);

            // link al frontend
            String link = urlFrontend + "/reset-password/" + token;

            String cuerpo = """
                Estimado usuario,<br><br>
                Hemos recibido una solicitud para restablecer su contraseña.<br><br>

                Para continuar, haga clic en el siguiente enlace:<br>
                <a href="%s">Restablecer contraseña</a><br><br>

                <strong>Nota:</strong> Este enlace es de un solo uso.
                Una vez utilizado, dejará de funcionar.<br><br>

                Si usted no solicitó este cambio, puede ignorar este correo.<br><br>

                Atentamente,<br>
                Su equipo de soporte
                """.formatted(link);

            emailService.enviarHtml(email, "Restablecimiento de contraseña", cuerpo);

            return ResponseEntity.ok("Correo de recuperación enviado");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/notificar-autorizacion")
    public ResponseEntity<String> notificarAutorizacion(@RequestBody EmailDevolucionRequest request) {
        emailService.enviarHtml(
                request.getEmail(),
                "Reembolso autorizado",
                "Su solicitud de reembolso con ID " + request.getIdTransaccion() + " ha sido autorizada."
        );
        return ResponseEntity.ok("Notificación enviada");
    }

    @PostMapping("/notificar-denegacion")
    public ResponseEntity<String> notificarDenegacion(@RequestBody EmailDevolucionRequest request) {
        emailService.enviarHtml(
                request.getEmail(),
                "Reembolso denegado",
                "Su solicitud de reembolso con ID " + request.getIdTransaccion() + " ha sido denegada."
        );
        return ResponseEntity.ok("Notificación enviada");
    }

    @PostMapping("/notificar-solicitud-reembolso")
    public ResponseEntity<String> notificarSolicitudReembolso(@RequestBody EmailDevolucionRequest request) {
        try {
            String cuerpo = """
            Estimado usuario,<br><br>
            Hemos recibido tu solicitud de reembolso con ID <strong>%s</strong>.<br><br>
            Nuestro equipo está revisando tu caso y te notificaremos
            cuando tengamos una resolución.<br><br>
            <strong>Nota:</strong> Este proceso puede tardar algunos días hábiles.<br><br>
            Si tienes dudas, contáctanos.<br><br>
            Atentamente,<br>Su equipo de soporte
            """.formatted(request.getIdTransaccion());

            emailService.enviarHtml(
                    request.getEmail(),
                    "Solicitud de reembolso recibida",
                    cuerpo
            );

            return ResponseEntity.ok("Notificación enviada");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}