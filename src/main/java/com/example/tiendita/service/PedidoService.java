package com.example.tiendita.service;

import com.example.tiendita.DTO.PedidoRequestDTO;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.domain.Compra;
import com.example.tiendita.domain.Pedido;
import com.example.tiendita.repository.ClienteRepository;
import com.example.tiendita.repository.CompraRepository;
import com.example.tiendita.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final CompraRepository compraRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         CompraRepository compraRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.compraRepository = compraRepository;
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public int hacerPedido(PedidoRequestDTO dto) {

        // 1. Buscar cliente por email
        Cliente cliente = clienteRepository.findByEmail(dto.getEmail()).orElse(null);
        if (cliente == null) return 0;

        // 2. Buscar compra por ID
        Compra compra = compraRepository.findById(dto.getID_Compra()).orElse(null);
        if (compra == null) return 0;

        // 3. Verificar si ya existe un pedido con misma compra y cliente
        boolean existe = pedidoRepository.existsByCompra_IdAndCliente_Id(
                compra.getId(), cliente.getId()
        );
        if (existe) return -1; // ya existe

        // 4. Crear pedido y copiar dirección del cliente
        Pedido pedido = new Pedido();
        pedido.setCompra(compra);
        pedido.setCliente(cliente);
        pedido.setFechaPedido(LocalDate.now());

        // Copiar dirección del DTO al pedido
        pedido.setCalle(dto.getCalle());
        pedido.setNumeroExterior(dto.getNumeroExterior() != null ?
                Integer.parseInt(dto.getNumeroExterior()) : null);
        pedido.setNumeroInterior(dto.getNumeroInterior() != null ?
                Integer.parseInt(dto.getNumeroInterior()) : null);
        pedido.setCiudad(dto.getCiudad());
        pedido.setEstadoOrigen(dto.getEstado());
        pedido.setEstado("Pendiente");
        pedido.setCodigoPostal(dto.getCodigoPostal() != null ?
                Integer.parseInt(dto.getCodigoPostal()) : null);
        pedido.setPais(dto.getPais());
        pedido.setInstruccionesExtras(dto.getInstruccionesExtras());

        pedidoRepository.save(pedido);
        return 1;
    }

    @Transactional
    public String cambiarEstadoPedido(String nuevoEstado, Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido == null) return "Pedido no encontrado";

        String estadoActual = pedido.getEstado();

        // Validar estados finales
        if ("Completado".equals(estadoActual)) return "El pedido ya fue Completado";
        if ("Cancelado".equals(estadoActual))  return "El pedido ya fue cancelado";

        // Solo se puede cambiar si está "En reparto o Pendiente"
        if (!"En reparto".equals(estadoActual) && !"Pendiente".equals(estadoActual)) {
            return "El estado actual no permite esta acción";
        }

        pedido.setEstado(nuevoEstado);

        // Si se completa, agregar fecha de entrega
        if ("Completado".equals(nuevoEstado)) {
            pedido.setFechaEntrega(LocalDate.now());
        }

        pedidoRepository.save(pedido);
        return "ok";
    }
}