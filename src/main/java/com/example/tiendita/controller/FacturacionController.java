package com.example.tiendita.controller;

import com.example.tiendita.DTO.FacturaDetalleDTO;
import com.example.tiendita.DTO.FacturaPosibleDTO;
import com.example.tiendita.DTO.FacturacionRequestDTO;
import com.example.tiendita.service.EmailService;
import com.example.tiendita.service.FacturacionService;
import com.example.tiendita.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facturacion")
public class FacturacionController {

    private final FacturacionService facturacionService;
    private final PdfService pdfService;
    private final EmailService emailService;

    public FacturacionController(FacturacionService facturacionService,
                                 PdfService pdfService, EmailService emailService) {
        this.facturacionService = facturacionService;
        this.pdfService = pdfService;
        this.emailService = emailService;
    }


    @GetMapping("/disponibles")
    public ResponseEntity<?> getFacturasDisponibles(@RequestParam String email) {
        List<FacturaPosibleDTO> result = facturacionService.getFacturasDisponibles(email);
        if (result.isEmpty()) return ResponseEntity.ok("{\"success\": 0}");
        return ResponseEntity.ok(result);
    }


    @PostMapping("/facturar")
    public ResponseEntity<String> facturar(@RequestBody FacturacionRequestDTO request) {
        try {
            Long resultado = facturacionService.facturar(request);

            return switch (resultado.intValue()) {
                case -1 -> ResponseEntity.status(404).body("Cliente no encontrado");
                case -2 -> ResponseEntity.status(404).body("Compra no encontrada");
                case -3 -> ResponseEntity.status(400).body("Ya existe una factura para esta compra");
                default -> ResponseEntity.ok("Factura creada con ID: " + resultado);
            };
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/obtener")
    public ResponseEntity<List<FacturaDetalleDTO>> obtenerFacturas() {
        try {
            return ResponseEntity.ok(facturacionService.obtenerFacturas());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
        FacturaDetalleDTO factura = facturacionService.obtenerFacturaPorId(id);
        if (factura == null) return ResponseEntity.notFound().build();

        byte[] pdf = pdfService.generarPdf("factura", Map.of("factura", factura));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"factura-" + id + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PatchMapping("/{id}/estatus")
    public ResponseEntity<String> actualizarEstatus(
            @PathVariable Long id,
            @RequestParam String estatus) {

        int resultado = facturacionService.actualizarEstatus(id, estatus);
        return switch (resultado) {
            case -1 -> ResponseEntity.badRequest()
                    .body("Estatus inválido. Usa: Pendiente, Aprobada o Rechazada");
            case 0 -> ResponseEntity.status(404).body("Factura no encontrada");
            default -> ResponseEntity.ok("Estatus actualizado a: " + estatus);
        };
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<String> enviarFactura(
            @PathVariable Long id,
            @RequestParam String email) {

        FacturaDetalleDTO factura = facturacionService.obtenerFacturaPorId(id);
        if (factura == null) return ResponseEntity.status(404).body("Factura no encontrada");

        byte[] pdf = pdfService.generarPdf("factura", Map.of("factura", factura));

        try {
            emailService.enviarConAdjunto(
                    email,
                    "Tu factura #" + id,
                    "<p>Hola, adjuntamos tu factura <strong>#" + id + "</strong>.</p>"
                            + "<p>Gracias por tu compra</p>",
                    pdf,
                    "factura-" + id + ".pdf"
            );
            return ResponseEntity.ok("Factura " + id + " enviada a " + email);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error al enviar el correo: " + e.getMessage());
        }
    }
}