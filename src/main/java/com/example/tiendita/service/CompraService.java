package com.example.tiendita.service;

import com.example.tiendita.DTO.CompraRequest;
import com.example.tiendita.domain.*;
import com.example.tiendita.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {


    private final ClienteRepository clienteRepository;
    private final CarritoRepository carritoRepository;
    private final ProductosEnCarritoRepository productosEnCarritoRepository;
    private final PagoRepository pagoRepository;
    private final CompraRepository compraRepository;
    private final ProductosEnCompraRepository productosEnCompraRepository;
    private final ProductoRepository productoRepository;

    public CompraService(
            ClienteRepository clienteRepository,
            CarritoRepository carritoRepository,
            ProductosEnCarritoRepository productosEnCarritoRepository,
            PagoRepository pagoRepository,
            CompraRepository compraRepository,
            ProductosEnCompraRepository productosEnCompraRepository,
            ProductoRepository productoRepository) {
        this.clienteRepository = clienteRepository;
        this.carritoRepository = carritoRepository;
        this.productosEnCarritoRepository = productosEnCarritoRepository;
        this.pagoRepository = pagoRepository;
        this.compraRepository = compraRepository;
        this.productosEnCompraRepository = productosEnCompraRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Long procesarCompra(CompraRequest request) {

        // 1. Buscar cliente
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // 2. Obtener carrito y sus productos
        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        List<ProductosEnCarrito> itemsCarrito = productosEnCarritoRepository.findByCarrito(carrito);

        if (itemsCarrito.isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }

        // 3. Validar que el total del request coincida con el calculado en BD
        BigDecimal totalCalculado = itemsCarrito.stream()
                .map(i -> i.getProducto().getPrecio()
                        .multiply(BigDecimal.valueOf(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal diferencia = totalCalculado.subtract(request.getTotal()).abs();
        BigDecimal tolerancia = new BigDecimal("0.01");

        if (diferencia.compareTo(tolerancia) > 0) {
            throw new IllegalArgumentException(
                    "El total no coincide. Esperado: " + totalCalculado + ", recibido: " + request.getTotal()
            );
        }

        // 4. Validar stock de todos los productos ANTES de operar
        for (ProductosEnCarrito item : itemsCarrito) {
            Producto producto = item.getProducto();
            if (producto.getStock() < item.getCantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para: " + producto.getNombre()
                );
            }
        }

        // 5. Insertar pago
        Pago pago = new Pago();
        pago.setCliente(cliente);
        pago.setFormaPago("Paypal");
        pago.setFechaPago(LocalDate.now());
        pago.setTotal(request.getTotal());
        pago.setIdPaypal(request.getIdPaypal());
        pago.setCorreoElectronicoPaypal(request.getCorreoPaypal());
        pago.setNombreClientePaypal(request.getClientePaypal());
        pago.setDireccionClientePaypal(request.getDireccionClientePaypal());
        pago = pagoRepository.save(pago);

        // 6. Insertar compra
        Compra compra = new Compra();
        compra.setPago(pago);
        compra = compraRepository.save(compra);

        // 7. Insertar en productosencompra y descontar stock
        List<ProductosEnCompra> pecs = new ArrayList<>();
        List<Producto> productosActualizados = new ArrayList<>();

        for (ProductosEnCarrito item : itemsCarrito) {
            ProductosEnCompra pec = new ProductosEnCompra();
            pec.setId(new ProductosEnCompraId(compra.getId(), item.getProducto().getId()));
            pec.setCompra(compra);
            pec.setProducto(item.getProducto());
            pec.setCantidad(item.getCantidad());
            pecs.add(pec);

            Producto producto = item.getProducto();
            producto.setStock(producto.getStock() - item.getCantidad());
            productosActualizados.add(producto);
        }

        productosEnCompraRepository.saveAll(pecs);
        productoRepository.saveAll(productosActualizados);

        // 8. Vaciar carrito
        productosEnCarritoRepository.deleteByCarrito(carrito);

        return compra.getId();
    }
}