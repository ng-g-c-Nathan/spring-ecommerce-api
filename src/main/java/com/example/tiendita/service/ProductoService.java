package com.example.tiendita.service;

import com.example.tiendita.DTO.ProductoNuevoRequest;
import com.example.tiendita.DTO.ProveedorMasGrande;
import com.example.tiendita.domain.Producto;
import com.example.tiendita.domain.Proveedor;
import com.example.tiendita.repository.ProductoRepository;
import com.example.tiendita.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;

    public ProductoService(ProductoRepository productoRepository, ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }
    public ProveedorMasGrande obtenerProveedorGrande() {

        return productoRepository
                .proveedorConMasProductos()
                .stream()
                .findFirst()
                .orElse(null);
    }
    public long contarProductos() {
        return productoRepository.count();
    }
    public List<Producto> productosDisponibles() {
        return productoRepository.findByStockGreaterThan(0);
    }
    public List<String> obtenerCategorias() {
        return productoRepository.findDistinctCategorias();
    }

    public List<String> obtenerCategoriasMenos(String categoria) {
        return productoRepository.findDistinctCategoriasExcluyendo(categoria);
    }

    public List<Producto> productosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public Long agregarProducto(ProductoNuevoRequest request) throws Exception {
        // Buscar proveedor
        Proveedor proveedor = proveedorRepository.findById(request.getAgregarProveedor())
                .orElseThrow(() -> new Exception("Proveedor no encontrado"));

        // Crear producto
        Producto producto = new Producto();
        producto.setNombre(request.getAgregarNombre());
        producto.setDescripcion(request.getAgregarDescripcion());
        producto.setPrecio(request.getAgregarPrecio());
        producto.setCategoria(request.getAgregarCategoria());
        producto.setProveedor(proveedor);
        producto.setStock(request.getAgregarStock());

        // (JPA se encarga del ID auto-increment)
        Producto guardado = productoRepository.save(producto);

        return guardado.getId(); // Devuelve el ID generado
    }
}