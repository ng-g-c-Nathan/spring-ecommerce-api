package com.example.tiendita.service;

import com.example.tiendita.DTO.*;
import com.example.tiendita.domain.Producto;
import com.example.tiendita.domain.Proveedor;
import com.example.tiendita.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ProveedorGrandeDTO obtenerProveedorGrande() {

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
    public void actualizarProducto(ActualizarProductoDTO dto) throws Exception {

        Producto producto = productoRepository.findById(dto.getEditarID())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        producto.setNombre(dto.getEditarNombre());
        producto.setDescripcion(dto.getEditarDescripcion());
        producto.setPrecio(dto.getEditarPrecio());
        producto.setCategoria(dto.getEditarCategoria());
        producto.setStock(dto.getEditarStock());

        if (dto.getEditarProveedor() == null) {
            throw new Exception("Proveedor inválido");
        }

        Proveedor proveedor = proveedorRepository
                .findById(dto.getEditarProveedor())
                .orElseThrow(() -> new Exception("Proveedor no encontrado"));

        producto.setProveedor(proveedor);

        productoRepository.save(producto);
    }

    @Transactional(readOnly = true)
    public List<TopProductoDTO> getTopDiez() {
        return productoRepository.findTopDiez();
    }

    public List<Producto> getProductosBaratos() {
        return productoRepository.findAllByOrderByPrecioAsc();
    }

    public List<Producto> getProductosCaros() {
        return productoRepository.findAllByOrderByPrecioDesc();
    }

    public List<Producto> obtenerInicio() {
        return productoRepository.findRandom();
    }
}