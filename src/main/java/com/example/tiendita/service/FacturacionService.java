package com.example.tiendita.service;

import com.example.tiendita.DTO.FacturaDetalleDTO;
import com.example.tiendita.DTO.FacturaPosibleDTO;
import com.example.tiendita.DTO.FacturacionRequestDTO;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.domain.Compra;
import com.example.tiendita.domain.Facturacion;
import com.example.tiendita.repository.ClienteRepository;
import com.example.tiendita.repository.CompraRepository;
import com.example.tiendita.repository.FacturacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FacturacionService {

    private final FacturacionRepository facturacionRepository;
    private final ClienteRepository     clienteRepository;
    private final CompraRepository      compraRepository;

    public FacturacionService(FacturacionRepository facturacionRepository,
                              ClienteRepository clienteRepository,
                              CompraRepository compraRepository) {
        this.facturacionRepository = facturacionRepository;
        this.clienteRepository     = clienteRepository;
        this.compraRepository      = compraRepository;
    }


    @Transactional(readOnly = true)
    public List<FacturaPosibleDTO> getFacturasDisponibles(String email) {
        return facturacionRepository.findFacturasDisponiblesByEmail(email);
    }


    @Transactional
    public Long facturar(FacturacionRequestDTO dto) {
        // 1. Buscar cliente por email
        Cliente cliente = clienteRepository.findByEmail(dto.getCorreo()).orElse(null);
        if (cliente == null) return -1L;   // cliente no encontrado

        // 2. Buscar compra
        Compra compra = compraRepository.findById(dto.getIdCompra()).orElse(null);
        if (compra == null) return -2L;    // compra no encontrada

        // 3. Verificar duplicado
        boolean existe = facturacionRepository
                .existsByCliente_IdAndCompra_Id(cliente.getId(), compra.getId());
        if (existe) return -3L;            // ya existe factura para esta compra

        // 4. Fechas: emisión hoy, vencimiento +7 días
        LocalDate hoy = LocalDate.now();

        // 5. Construir entidad — dirección + datos fiscales
        Facturacion f = new Facturacion();
        f.setCliente(cliente);
        f.setCompra(compra);
        f.setFechaEmision(hoy);
        f.setFechaVencimiento(hoy.plusDays(7));
        f.setEstatus("Pendiente");

        // Dirección de facturación (campos heredados)
        f.setCalle(dto.getCalle());
        f.setNumeroExterior(parseSafe(dto.getNumeroExterior()));
        f.setNumeroInterior(parseSafe(dto.getNumeroInterior()));
        f.setCiudad(dto.getCiudad());
        f.setEstado(dto.getEstado());
        f.setCodigoPostal(parseSafe(dto.getCodigoPostal()));
        f.setPais(dto.getPais() != null ? dto.getPais() : "México");

        // Datos fiscales (nuevos campos camelCase del frontend)
        f.setRfc(dto.getRfc() != null ? dto.getRfc().toUpperCase() : null);
        f.setRazonSocial(dto.getRazonSocial());
        f.setRegimenFiscal(dto.getRegimenFiscal());
        f.setCodigoPostalFiscal(parseSafe(dto.getCodigoPostalFiscal()));
        f.setUsoCfdi(dto.getUsoCfdi());
        f.setCorreoFacturacion(dto.getCorreoFacturacion());

        return facturacionRepository.save(f).getId();
    }


    @Transactional(readOnly = true)
    public List<FacturaDetalleDTO> obtenerFacturas() {
        return facturacionRepository.findAllFacturasDetalle()
                .stream()
                .map(v -> new FacturaDetalleDTO(
                        v.getIdFacturacion(),
                        v.getFechaEmision(),
                        v.getFechaVencimiento(),
                        v.getIdCompra(),
                        v.getEmail(),
                        v.getDireccion(),
                        v.getNombreCompleto(),
                        v.getRfc(),
                        v.getDetalles(),
                        v.getTotalProductos(),
                        v.getEstatus()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public FacturaDetalleDTO obtenerFacturaPorId(Long id) {
        return facturacionRepository.findFacturaDetalleById(id)
                .stream()
                .findFirst()
                .map(v -> new FacturaDetalleDTO(
                        v.getIdFacturacion(),
                        v.getFechaEmision(),
                        v.getFechaVencimiento(),
                        v.getIdCompra(),
                        v.getEmail(),
                        v.getDireccion(),
                        v.getNombreCompleto(),
                        v.getRfc(),
                        v.getDetalles(),
                        v.getTotalProductos(),
                        v.getEstatus()
                ))
                .orElse(null);
    }


    @Transactional
    public int actualizarEstatus(Long id, String estatus) {
        List<String> permitidos = List.of("Pendiente", "Aprobada", "Rechazada");
        if (!permitidos.contains(estatus)) return -1;
        return facturacionRepository.updateEstatus(id, estatus);
    }


    private Integer parseSafe(String value) {
        if (value == null || value.isBlank()) return null;
        try { return Integer.parseInt(value.trim()); }
        catch (NumberFormatException e) { return null; }
    }

    private FacturaDetalleDTO mapRow(Object[] row) {
        return new FacturaDetalleDTO(
                ((Number) row[0]).longValue(),   // idFacturacion
                (String)  row[1],                // fechaEmision
                (String)  row[2],                // fechaVencimiento
                ((Number) row[3]).longValue(),   // idCompra
                (String)  row[4],                // email
                (String)  row[5],                // direccion
                (String)  row[6],                // nombreCompleto
                (String)  row[7],                // rfc
                (String)  row[8],                // detalles
                ((Number) row[9]).longValue(),   // totalProductos
                (String)  row[10]                // estatus
        );
    }
}