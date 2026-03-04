package com.example.tiendita.service;

import com.example.tiendita.DTO.ProductoNuevoRequest;
import com.example.tiendita.DTO.ProductosConProveedorDTO;
import com.example.tiendita.DTO.ProveedorMasGrandeDto;
import com.example.tiendita.domain.Producto;
import com.example.tiendita.domain.Proveedor;
import com.example.tiendita.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductosEnCarritoRepository productosEnCarritoRepository;
    private final ProductosEnListaDeseosRepository productosEnListaDeseosRepository;
    private final ResenaRepository resenaRepository;

    public ProductoService(ProductoRepository productoRepository, ProveedorRepository proveedorRepository, ProductosEnCarritoRepository productosEnCarritoRepository,ProductosEnListaDeseosRepository productosEnListaDeseosRepository,ResenaRepository resenaRepository ) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.productosEnCarritoRepository = productosEnCarritoRepository;
        this.productosEnListaDeseosRepository=productosEnListaDeseosRepository;
        this.resenaRepository=resenaRepository;
    }
    public ProveedorMasGrandeDto obtenerProveedorGrande() {

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
    public Optional<ProductosConProveedorDTO> consultarProductoEspecial(Long id) {
        return productoRepository.consultarProductoEspecial(id);
    }
    @Transactional
    public void borrarProducto(String idProducto) {

        Long id = Long.parseLong(idProducto);

        // primero borramos referencias
        productosEnCarritoRepository.deleteByProductoId(id);
        productosEnListaDeseosRepository.deleteByProductoId(id);
        resenaRepository.deleteByProductoId(id);

        // ahora sí el producto
        productoRepository.deleteById(id);
    }

    @Transactional
    public void actualizarProducto(Producto data) throws Exception {

        Producto producto = productoRepository.findById(data.getId())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        producto.setNombre(data.getNombre());
        producto.setDescripcion(data.getDescripcion());
        producto.setPrecio(data.getPrecio());
        producto.setCategoria(data.getCategoria());
        producto.setStock(data.getStock());

        if (data.getProveedor() == null || data.getProveedor().getId() == null) {
            throw new Exception("Proveedor inválido");
        }

        Proveedor proveedor = proveedorRepository
                .findById(data.getProveedor().getId())
                .orElseThrow(() -> new Exception("Proveedor no encontrado"));

        producto.setProveedor(proveedor);

        productoRepository.save(producto);
    }
}