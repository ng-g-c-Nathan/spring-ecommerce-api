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
public class CarritoService {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final CarritoRepository carritoRepository;
    private final ProductosEnCarritoRepository productosEnCarritoRepository;
    private final ListaDeseosRepository listaDeseosRepository;
    private final ProductosEnListaDeseosRepository productosEnListaDeseosRepository;

    public CarritoService(ClienteRepository clienteRepository, ProductoRepository productoRepository, CarritoRepository carritoRepository, ProductosEnCarritoRepository productosEnCarritoRepository,ListaDeseosRepository listaDeseosRepository,ProductosEnListaDeseosRepository productosEnListaDeseosRepository) {
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.carritoRepository = carritoRepository;
        this.productosEnCarritoRepository = productosEnCarritoRepository;
        this.listaDeseosRepository= listaDeseosRepository;
        this.productosEnListaDeseosRepository=productosEnListaDeseosRepository;
    }

    @Transactional
    public void agregarAlCarrito(ManejarProductoAlUsuarioRequest request) throws Exception {
        //Buscar cliente
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        //Buscar producto
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        //Obtener carrito del cliente
        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Carrito del cliente no encontrado"));

        //Obtener producto en carrito
        ProductosEnCarrito item = productosEnCarritoRepository
                .findByCarritoAndProducto(carrito, producto)
                .orElse(null);

        int cantidadActual = item != null ? item.getCantidad() : 0;

        //Verificar stock
        if (cantidadActual + 1 > producto.getStock()) {
            throw new Exception("No hay suficiente stock disponible para agregar más del producto al carrito");
        }

        // Insertar o actualizar
        if (item == null) {
            ProductosEnCarrito nuevoItem = new ProductosEnCarrito();

            //Crear el ID compuesto
            ProductosEnCarritoId nuevoId = new ProductosEnCarritoId(carrito.getId(), producto.getId());
            nuevoItem.setId(nuevoId);

            //Asignar las relaciones y cantidad
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(1);

            //Guardar
            productosEnCarritoRepository.save(nuevoItem);
        } else {
            item.setCantidad(cantidadActual + 1);
            productosEnCarritoRepository.save(item);
        }
        // Exito
    }
    @Transactional
    public void pasarALista(TransferirProductosRequest request) throws Exception {
        // Buscar cliente
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        // Buscar producto
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        // Obtener lista de deseos del cliente
        ListaDeseos lista = listaDeseosRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Lista de deseos del cliente no encontrada"));

        // Verificar si ya existe el producto en la lista
        boolean existe = productosEnListaDeseosRepository
                .findByListaDeseosAndProducto(lista, producto)
                .isPresent();

        if (!existe) {
            // Crear item nuevo
            ProductosEnListaDeseos item = new ProductosEnListaDeseos();

            // Crear el ID compuesto manualmente
            ProductosEnListaDeseosId nuevoId = new ProductosEnListaDeseosId(lista.getId(), producto.getId());
            item.setId(nuevoId);

            // Asignar relaciones y cantidad
            item.setListaDeseos(lista);
            item.setProducto(producto);
            item.setCantidad(request.getCantidad());

            // Guardar
            productosEnListaDeseosRepository.save(item);
        }
        // Si ya existe, no hacemos nada
    }

    @Transactional
    public List<ProductosDelClienteDTO> miCarrito(String email) throws Exception {

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        List<ProductosDelClienteDTO> productos =
                productosEnCarritoRepository.obtenerMiCarrito(carrito);

        // Recorremos la lista y quitamos los que ya no se pueden comprar
        Iterator<ProductosDelClienteDTO> it = productos.iterator();

        while (it.hasNext()) {

            ProductosDelClienteDTO item = it.next();

            if (item.getStock() == 0) {

                Producto producto = productoRepository
                        .findById(item.getId())
                        .orElseThrow();

                productosEnCarritoRepository
                        .findByCarritoAndProducto(carrito, producto)
                        .ifPresent(productosEnCarritoRepository::delete);

                // no lo regresamos al front
                it.remove();
            }
        }

        return productos;
    }

    @Transactional
    public void borrarProductoDelCarrito(ManejarProductoAlUsuarioRequest dto)
            throws Exception {

        Cliente cliente = clienteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        productosEnCarritoRepository
                .findByCarritoAndProducto(carrito, producto)
                .ifPresent(productosEnCarritoRepository::delete);
    }
    @Transactional
    public void actualizarCantidadCarrito(ActualizarCantidadRequest dto)
            throws Exception {

        Cliente cliente = clienteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        Producto producto = productoRepository.findById(dto.getProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        if (dto.getValorNuevo() > producto.getStock()) {
            throw new Exception(
                    "No hay suficiente stock disponible para actualizar la cantidad en el carrito"
            );
        }

        ProductosEnCarrito item = productosEnCarritoRepository
                .findByCarritoAndProducto(carrito, producto)
                .orElseThrow(() -> new Exception("Producto no está en el carrito"));

        item.setCantidad(dto.getValorNuevo());

        productosEnCarritoRepository.save(item);
    }

}