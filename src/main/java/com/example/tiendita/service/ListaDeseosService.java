package com.example.tiendita.service;

import com.example.tiendita.DTO.ActualizarCantidadRequest;
import com.example.tiendita.DTO.ManejarProductoAlUsuarioRequest;
import com.example.tiendita.DTO.ProductosDelClienteDTO;
import com.example.tiendita.DTO.TransferirProductosRequest;
import com.example.tiendita.domain.*;
import com.example.tiendita.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ListaDeseosService {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final ListaDeseosRepository listaDeseosRepository;
    private final ProductosEnListaDeseosRepository productosEnListaDeseosRepository;
    private final CarritoRepository carritoRepository;
    private final ProductosEnCarritoRepository productosEnCarritoRepository;

    public ListaDeseosService(ClienteRepository clienteRepository, ProductoRepository productoRepository, ListaDeseosRepository listaDeseosRepository, ProductosEnListaDeseosRepository productosEnListaDeseosRepository,CarritoRepository carritoRepository,ProductosEnCarritoRepository productosEnCarritoRepository) {
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.listaDeseosRepository = listaDeseosRepository;
        this.productosEnListaDeseosRepository = productosEnListaDeseosRepository;
        this.carritoRepository=carritoRepository;
        this.productosEnCarritoRepository=productosEnCarritoRepository;
    }

    @Transactional
    public void agregarALaLista(ManejarProductoAlUsuarioRequest request) throws Exception {
        // Buscar cliente
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        // Buscar producto
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        // Obtener lista de deseos del cliente
        ListaDeseos lista = listaDeseosRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Lista de deseos del cliente no encontrada"));

        // Obtener producto en la lista de deseos
        ProductosEnListaDeseos item = productosEnListaDeseosRepository
                .findByListaDeseosAndProducto(lista, producto)
                .orElse(null);

        int cantidadActual = item != null ? item.getCantidad() : 0;

        // Verificar stock
        if (cantidadActual + 1 > producto.getStock()) {
            throw new Exception("No hay suficiente stock disponible para agregar más del producto a la lista de deseos");
        }

        // Insertar o actualizar
        if (item == null) {
            ProductosEnListaDeseos nuevoItem = new ProductosEnListaDeseos();

            // Crear el ID compuesto
            ProductosEnListaDeseosId nuevoId = new ProductosEnListaDeseosId(lista.getId(), producto.getId());
            nuevoItem.setId(nuevoId);

            // Asignar relaciones y cantidad
            nuevoItem.setListaDeseos(lista);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(1);

            // Guardar
            productosEnListaDeseosRepository.save(nuevoItem);
        } else {
            item.setCantidad(cantidadActual + 1);
            productosEnListaDeseosRepository.save(item);
        }
    }

    @Transactional
    public void pasarCarro(TransferirProductosRequest request) throws Exception {
        // Buscar cliente
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        // Buscar producto
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        // Obtener carrito del cliente
        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Carrito del cliente no encontrado"));

        // Verificar si ya existe el producto en el carrito
        boolean existe = productosEnCarritoRepository
                .findByCarritoAndProducto(carrito, producto)
                .isPresent();

        if (!existe) {
            ProductosEnCarrito item = new ProductosEnCarrito();

            // Crear ID compuesto manualmente
            ProductosEnCarritoId nuevoId = new ProductosEnCarritoId(carrito.getId(), producto.getId());
            item.setId(nuevoId);

            //Asignar las asociaciones necesarias para @MapsId
            item.setCarrito(carrito);
            item.setProducto(producto);

            // Asignar cantidad
            item.setCantidad(request.getCantidad());

            // Guardar
            productosEnCarritoRepository.save(item);
        }
    }

    @Transactional
    public List<ProductosDelClienteDTO> miLista(String email) throws Exception {

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        ListaDeseos lista = listaDeseosRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Lista de deseos no encontrada"));

        List<ProductosDelClienteDTO> productos =
                productosEnListaDeseosRepository.obtenerMiLista(lista);

        Iterator<ProductosDelClienteDTO> it = productos.iterator();

        while (it.hasNext()) {

            ProductosDelClienteDTO item = it.next();

            if (item.getStock() == 0) {

                Producto producto = productoRepository
                        .findById(item.getId())
                        .orElseThrow();

                productosEnListaDeseosRepository
                        .findByListaDeseosAndProducto(lista, producto)
                        .ifPresent(productosEnListaDeseosRepository::delete);

                it.remove();
            }
        }

        return productos;
    }

    @Transactional
    public void borrarProductoDeLista(ManejarProductoAlUsuarioRequest dto)
            throws Exception {

        Cliente cliente = clienteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        ListaDeseos lista = listaDeseosRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Lista de deseos no encontrada"));

        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        productosEnListaDeseosRepository
                .findByListaDeseosAndProducto(lista, producto)
                .ifPresent(productosEnListaDeseosRepository::delete);
    }

    @Transactional
    public void actualizarCantidadLista(ActualizarCantidadRequest dto)
            throws Exception {

        Cliente cliente = clienteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        ListaDeseos lista = listaDeseosRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Lista no encontrada"));

        Producto producto = productoRepository.findById(dto.getProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        if (dto.getValorNuevo() > producto.getStock()) {
            throw new Exception(
                    "No hay suficiente stock disponible para actualizar la cantidad en la lista de deseos"
            );
        }

        ProductosEnListaDeseos item = productosEnListaDeseosRepository
                .findByListaDeseosAndProducto(lista, producto)
                .orElseThrow(() -> new Exception("Producto no está en la lista"));

        item.setCantidad(dto.getValorNuevo());

        productosEnListaDeseosRepository.save(item);
    }
}