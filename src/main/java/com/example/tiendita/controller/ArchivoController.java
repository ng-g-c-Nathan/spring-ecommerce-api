package com.example.tiendita.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/archivos")
public class ArchivoController {

    private final String BASE_PATH = "uploads/";

    // Subir archivo(foto o gif)
    @PostMapping("/subir")
    public ResponseEntity<String> subirArchivo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("esProducto") boolean esProducto,
            @RequestParam("id") int id) {

        try {
            String carpeta = esProducto ? "productos/" : "users/";
            String extension = esProducto ? ".gif" : ".png";
            String nombreArchivo = (esProducto ? "Imagen" : "User") + id + extension;

            Path rutaCompleta = Paths.get(BASE_PATH + carpeta + nombreArchivo);
            Files.createDirectories(rutaCompleta.getParent());
            Files.write(rutaCompleta, file.getBytes());

            return ResponseEntity.ok("Archivo guardado: " + nombreArchivo);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al guardar: " + e.getMessage());
        }
    }

    // Actualizas (es lo mismo que subir, sobreescribe el archivo)
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarArchivo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("esProducto") boolean esProducto,
            @RequestParam("id") int id) {

        // Misma lógica que subir — sobreescribe el archivo existente
        return subirArchivo(file, esProducto, id);
    }

    // Eliminar archivo
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarArchivo(
            @RequestParam("esProducto") boolean esProducto,
            @RequestParam("id") int id) {

        try {
            String carpeta = esProducto ? "productos/" : "users/";
            String extension = esProducto ? ".gif" : ".png";
            String nombreArchivo = (esProducto ? "Imagen" : "User") + id + extension;

            Path ruta = Paths.get(BASE_PATH + carpeta + nombreArchivo);
            Files.deleteIfExists(ruta);

            return ResponseEntity.ok("Archivo eliminado: " + nombreArchivo);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al eliminar: " + e.getMessage());
        }
    }

    // Ver el archivo
    @GetMapping("/ver/{carpeta}/{nombreArchivo}")
    public ResponseEntity<Resource> verArchivo(
            @PathVariable String carpeta,
            @PathVariable String nombreArchivo) throws IOException {

        Path ruta = Paths.get(BASE_PATH + carpeta + "/" + nombreArchivo);
        Resource resource = new UrlResource(ruta.toUri());

        if (!resource.exists()) return ResponseEntity.notFound().build();

        String contentType = nombreArchivo.endsWith(".gif") ? "image/gif" : "image/png";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/assets/**")
    public ResponseEntity<Resource> servirArchivo(HttpServletRequest request) throws IOException {

        String rutaSolicitada = request.getRequestURI().replace("/archivos/assets/", "");

        // Seguridad básica
        if (rutaSolicitada.contains("..")) {
            return ResponseEntity.badRequest().build();
        }

        Path ruta = Paths.get(BASE_PATH).resolve(rutaSolicitada).normalize();
        Resource recurso = new UrlResource(ruta.toUri());

        if (!recurso.exists() || !recurso.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        // Detectar MIME automáticamente
        String contentType = Files.probeContentType(ruta);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))

                // Cache en navegador por 1 día
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))

                .body(recurso);
    }
}
