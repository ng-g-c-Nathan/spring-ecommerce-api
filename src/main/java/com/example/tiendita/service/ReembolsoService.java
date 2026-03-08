package com.example.tiendita.service;

import com.example.tiendita.DTO.DevolucionPosibleDTO;
import com.example.tiendita.DTO.ReembolsoRequestDTO;
import com.example.tiendita.domain.*;
import com.example.tiendita.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReembolsoService {

    private final ReembolsoRepository reembolsoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final PagoRepository pagoRepository;
    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;

    public ReembolsoService(ReembolsoRepository reembolsoRepository,
                            ClienteRepository clienteRepository,
                            PedidoRepository pedidoRepository,
                            PagoRepository pagoRepository,CompraRepository compraRepository, ProductoRepository productoRepository) {
        this.reembolsoRepository = reembolsoRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagoRepository = pagoRepository;
        this.compraRepository=compraRepository;
        this.productoRepository=productoRepository;
    }

    @Transactional(readOnly = true)
    public List<DevolucionPosibleDTO> getDevolucionesByEmail(String email) {
        return reembolsoRepository.findDevolucionesByEmail(email);
    }

    public boolean existeReembolso(Long idTransaccion, Long idCompra, Long idProducto) {
        return reembolsoRepository.existsByPago_IdAndCompra_IdAndProducto_Id(
                idTransaccion, idCompra, idProducto
        );
    }

    @Transactional
    public Long hacerReembolso(ReembolsoRequestDTO dto) {

        // 1. Verificar si ya existe el reembolso
        boolean existe = reembolsoRepository.existsByPago_IdAndCompra_IdAndProducto_Id(
                dto.getIdTransaccion(), dto.getIdCompra(), dto.getIdProducto());

        if (existe) return 0L;

        // 2. Verificar que el pedido esté entregado (no "En reparto")
        boolean pedidoValido = pedidoRepository.existsByCompra_IdAndEstado(dto.getIdCompra(), "Completado");
        if (!pedidoValido) return -1L;

        // 3. Verificar que no hayan pasado más de 3 días desde el pago
        LocalDate fechaPago = pagoRepository.findFechaPagoByIdTransaccion(dto.getIdTransaccion());
        if (fechaPago == null) throw new IllegalArgumentException("Transacción no encontrada");
        long diasDiferencia = ChronoUnit.DAYS.between(fechaPago, LocalDate.now());
        if (diasDiferencia > 3) return 0L;

        // 4. Obtener objetos completos
        Cliente cliente = clienteRepository.findByEmail(dto.getCorreo()).orElse(null);
        if (cliente == null) return 0L;

        Pago pago = pagoRepository.findById(dto.getIdTransaccion()).orElse(null);
        Compra compra = compraRepository.findById(dto.getIdCompra()).orElse(null);
        Producto producto = productoRepository.findById(dto.getIdProducto()).orElse(null);

        if (pago == null || compra == null || producto == null) return 0L;

        // 5. Insertar reembolso con objetos completos
        Reembolso reembolso = new Reembolso();
        reembolso.setPago(pago);
        reembolso.setCompra(compra);
        reembolso.setProducto(producto);
        reembolso.setFechaReembolso(LocalDate.now());
        reembolso.setMonto(BigDecimal.valueOf(dto.getMonto()));
        reembolso.setMotivo(dto.getRazon());
        reembolso.setAutorizada("NO");

        Reembolso saved = reembolsoRepository.save(reembolso);
        return saved.getId();
    }

    @Transactional
    public boolean autorizarDevolucion(Long id) {
        Reembolso reembolso = reembolsoRepository.findById(id).orElse(null);
        if (reembolso == null) return false;

        reembolso.setAutorizada("SI");
        reembolsoRepository.save(reembolso);
        return true;
    }

    @Transactional
    public int denegarDevolucion(Long id) {
        Reembolso reembolso = reembolsoRepository.findById(id).orElse(null);

        if (reembolso == null) return 0;

        // Si ya está autorizada no se puede denegar
        if ("SI".equals(reembolso.getAutorizada())) return -1;

        reembolsoRepository.delete(reembolso);
        return 1;
    }

    @Transactional(readOnly = true)
    public List<Reembolso> getAllReembolsos() {
        return reembolsoRepository.findAll();
    }
}
