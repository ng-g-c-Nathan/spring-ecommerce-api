package com.example.tiendita.controller;

import com.example.tiendita.DTO.ActualizarProductoDTO;
import com.example.tiendita.DTO.ProductoNuevoRequest;
import com.example.tiendita.DTO.ProveedorGrandeDTO;
import com.example.tiendita.DTO.TopProductoDTO;
import com.example.tiendita.domain.Producto;
import com.example.tiendita.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/proveedor-grande")
    public ProveedorGrandeDTO proveedorGrande() {
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
    @GetMapping("/producto/{id}")
    public ResponseEntity<?> consultarProducto(@PathVariable Long id) {

        return productoService
                .consultarProductoEspecial(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(Map.of("success", 0)));
    }

    @PostMapping("/borrarProducto")
    public ResponseEntity<?> borrarProducto(@RequestBody String idProducto) {

        try {
            productoService.borrarProducto(idProducto.trim());
            return ResponseEntity.ok(Map.of("success", 1));

        } catch (Exception e) {
            return ResponseEntity.ok(
                    Map.of("success", 0, "message", e.getMessage())
            );
        }
    }

    @PostMapping("/actualizarProducto")
    public ResponseEntity<?> actualizarProducto(@RequestBody ActualizarProductoDTO dto) {
        try {
            productoService.actualizarProducto(dto);
            return ResponseEntity.ok(Map.of("success", 1));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("success", 0, "message", e.getMessage()));
        }
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopDiez() {
        List<TopProductoDTO> result = productoService.getTopDiez();

        if (result.isEmpty()) {
            return ResponseEntity.ok().body("{\"success\": 0}");
        }

        return ResponseEntity.ok(result);
    }
    @GetMapping("/baratos")
    public ResponseEntity<?> getBaratos() {
        List<Producto> result = productoService.getProductosBaratos();
        return result.isEmpty()
                ? ResponseEntity.ok("{\"success\": 0}")
                : ResponseEntity.ok(result);
    }

    @GetMapping("/caros")
    public ResponseEntity<?> getCaros() {
        List<Producto> result = productoService.getProductosCaros();
        return result.isEmpty()
                ? ResponseEntity.ok("{\"success\": 0}")
                : ResponseEntity.ok(result);
    }

    @GetMapping("/inicio")
    public ResponseEntity<?> inicio() {
        List<Producto> productos = productoService.obtenerInicio();

        if (productos.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("success", 0));
        }

        return ResponseEntity.ok(productos);
    }
}