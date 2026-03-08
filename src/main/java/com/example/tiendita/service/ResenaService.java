package com.example.tiendita.service;

import com.example.tiendita.DTO.ResenaDTO;
import com.example.tiendita.DTO.ResenaRequest;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.domain.Producto;
import com.example.tiendita.domain.Resena;
import com.example.tiendita.repository.ClienteRepository;
import com.example.tiendita.repository.ProductoRepository;
import com.example.tiendita.repository.ProductosEnCompraRepository;
import com.example.tiendita.repository.ResenaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResenaService {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final ResenaRepository resenaRepository;
    private final ProductosEnCompraRepository productosEnCompraRepository;

    public ResenaService(ClienteRepository clienteRepository, ProductoRepository productoRepository, ResenaRepository resenaRepository,ProductosEnCompraRepository productosEnCompraRepository) {
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.resenaRepository = resenaRepository;
        this.productosEnCompraRepository=productosEnCompraRepository;
    }

    @Transactional
    public void meterResena(ResenaRequest request) {


        Cliente cliente = clienteRepository
                .findByEmail(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Producto producto = productoRepository
                .findById(request.getProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (resenaRepository.existsByCliente_IdAndProducto_Id(cliente.getId(), producto.getId())) {
            throw new RuntimeException("Ya existe una reseña para este producto");
        }

        Resena resena = new Resena();
        resena.setCliente(cliente);
        resena.setProducto(producto);
        resena.setComentario(request.getComentario());
        resena.setCalificacion(request.getCalificacion());

        resenaRepository.save(resena);
    }
    public long contarResenas() {
        return resenaRepository.count();
    }

    public List<ResenaDTO> obtenerResenasProducto(Long idProducto) {
        return resenaRepository.buscarResenasProducto(idProducto);
    }

    @Transactional(readOnly = true)
    public boolean tieneDerechoResena(String email, Long idProducto) {
        return productosEnCompraRepository.existsCompraByEmailAndProducto(email, idProducto);
    }
}