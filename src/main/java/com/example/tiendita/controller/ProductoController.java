package com.example.tiendita.controller;

import com.example.tiendita.DTO.ProductoNuevoRequest;
import com.example.tiendita.DTO.ProveedorMasGrande;
import com.example.tiendita.domain.Producto;
import com.example.tiendita.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/proveedor-grande")
    public ProveedorMasGrande proveedorGrande() {
        return productoService.obtenerProveedorGrande();
    }

    @GetMapping("/count")
    public long contarProductos() {
        return productoService.contarProductos();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> consultarProductos() {
        List<Producto> productos = productoService.productosDisponibles();
        if (productos.isEmpty()) {
            return ResponseEntity.ok().body("{\"success\":0}");
        } else {
            return ResponseEntity.ok(productos); // Spring serializa automáticamente a JSON
        }
    }

    @GetMapping("/categorias")
    public ResponseEntity<?> categorias() {
        List<String> categorias = productoService.obtenerCategorias();
        if (categorias.isEmpty()) {
            return ResponseEntity.ok("{\"success\":0}");
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/categorias-menos")
    public ResponseEntity<?> categoriasMenos(@RequestParam String categoria) {
        List<String> categorias = productoService.obtenerCategoriasMenos(categoria);
        if (categorias.isEmpty()) {
            return ResponseEntity.ok("{\"success\":0}");
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/por-categoria")
    public ResponseEntity<?> productosPorCategoria(@RequestParam String categoria) {
        List<Producto> productos = productoService.productosPorCategoria(categoria);
        if (productos.isEmpty()) {
            return ResponseEntity.ok(
                    "{\"success\":0, \"message\":\"No se encontraron productos para esta categoría\"}"
            );
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> agregarProductoNuevo(@RequestBody ProductoNuevoRequest request) {
        try {
            Long idProducto = productoService.agregarProducto(request);
            return ResponseEntity.ok("{\"success\":1,\"id_producto\":" + idProducto + "}");
        } catch (Exception e) {
            return ResponseEntity.ok("{\"success\":0,\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}