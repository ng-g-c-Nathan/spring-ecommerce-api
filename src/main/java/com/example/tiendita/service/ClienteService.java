package com.example.tiendita.service;

import com.example.tiendita.DTO.ActualizarPersonaRequest;
import com.example.tiendita.DTO.ClienteRegisterRequest;
import com.example.tiendita.DTO.ClienteResponseDTO;
import com.example.tiendita.domain.*;
import com.example.tiendita.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CarritoRepository carritoRepository;
    private final ListaDeseosRepository listaDeseosRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final VerificarRepository verificarRepository;
    private final RestablecerRepository restablecerRepository;

    public ClienteService(ClienteRepository clienteRepository, CarritoRepository carritoRepository, ListaDeseosRepository listaDeseosRepository, BCryptPasswordEncoder passwordEncoder, VerificarRepository verificarRepository, RestablecerRepository restablecerRepository) {
        this.clienteRepository = clienteRepository;
        this.verificarRepository = verificarRepository;
        this.carritoRepository = carritoRepository;
        this.listaDeseosRepository = listaDeseosRepository;
        this.passwordEncoder = passwordEncoder;
        this.restablecerRepository = restablecerRepository;
    }

    @Transactional
    public void agregarCliente(ClienteRegisterRequest dto) throws Exception {

        if (dto.getEmail() == null || dto.getEmail().isEmpty() ||
                dto.getContrasena() == null || dto.getContrasena().isEmpty()) {
            throw new Exception("El Email o la contraseña no pueden estar vacíos.");
        }

        // Verificar si ya existe
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("El correo electrónico ya está registrado.");
        }

        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setEmail(dto.getEmail());
        cliente.setContrasena(passwordEncoder.encode(dto.getContrasena())); // mejor que MD5
        cliente.setPermisos("NO");
        cliente.setVerificacion("NO");
        clienteRepository.save(cliente);

        // Crear carrito
        Carrito carrito = new Carrito();
        carrito.setCliente(cliente);
        carritoRepository.save(carrito);

        // Crear lista de deseos
        ListaDeseos listaDeseos = new ListaDeseos();
        listaDeseos.setCliente(cliente);
        listaDeseosRepository.save(listaDeseos);
    }

    public void login(String email, String password) throws Exception {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado."));

        if (!passwordEncoder.matches(password, cliente.getContrasena())) {
            throw new Exception("Datos incorrectos.");
        }

        if (!"SI".equalsIgnoreCase(cliente.getVerificacion())) {
            throw new Exception("Cuenta no verificada.");
        }

    }

    public boolean emailExiste(String email) {
        return clienteRepository.existsByEmail(email);
    }

    public Optional<Cliente> buscarPorCorreo(String correo) {
        return clienteRepository.findByEmail(correo);
    }

    @Transactional
    public void actualizarPersona(ActualizarPersonaRequest req) {

        Cliente cliente = clienteRepository
                .findByEmail(req.getCorreo())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        String correoActual = cliente.getEmail();

        // Solo actualizamos campos que no sean null
        if (req.getNombre() != null) cliente.setNombre(req.getNombre());
        if (req.getApellidoP() != null) cliente.setApellidoPaterno(req.getApellidoP());
        if (req.getApellidoM() != null) cliente.setApellidoMaterno(req.getApellidoM());
        if (req.getApodo() != null) cliente.setApodo(req.getApodo());
        if (req.getCalle() != null) cliente.setCalle(req.getCalle());
        if (req.getNumeroExterior() != null) cliente.setNumeroExterior(req.getNumeroExterior());
        if (req.getNumeroInterior() != null) cliente.setNumeroInterior(req.getNumeroInterior());
        if (req.getCiudad() != null) cliente.setCiudad(req.getCiudad());
        if (req.getEstado() != null) cliente.setEstado(req.getEstado());
        if (req.getCodigoPostal() != null) cliente.setCodigoPostal(req.getCodigoPostal());
        if (req.getPais() != null) cliente.setPais(req.getPais());
        if (req.getInstruccionesExtras() != null) cliente.setInstruccionesExtras(req.getInstruccionesExtras());
        if (req.getTelefono() != null) cliente.setTelefono(req.getTelefono());
        if (req.getRfc() != null) cliente.setRfc(req.getRfc());

        // Lógica del correo
        boolean correoCambio = req.getCorreoNuevo() != null && !correoActual.equals(req.getCorreoNuevo());
        if (req.getCorreoNuevo() != null) cliente.setEmail(req.getCorreoNuevo());

        if (correoCambio) {
            cliente.setVerificacion("NO");
            // reactivarCuenta(cliente);
        }

        clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true) //Transaccion pero unicamente de lectura
    public int revisarAdmin(String email) {

        String correo = email.replace("\"", "").trim();

        Optional<Cliente> clienteOpt =
                clienteRepository.findByEmail(correo);

        // success = 3 → no existe
        if (clienteOpt.isEmpty()) {
            return 3;
        }

        Cliente cliente = clienteOpt.get();

        // success = 1 → es admin
        if ("SI".equalsIgnoreCase(cliente.getPermisos())) {
            return 1;
        }

        // success = 2 → existe pero no es admin
        return 2;
    }

    @Transactional(readOnly = true)
    public Long getIdByEmail(String email) {
        return clienteRepository.findByEmail(email)
                .map(Cliente::getId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún cliente con el email proporcionado"));
    }

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public void guardarTokenVerificacion(Cliente cliente, Long token) {
        Verificar verificar = new Verificar();
        verificar.setId(token);
        verificar.setCliente(cliente);
        verificarRepository.save(verificar);
    }

    @Transactional
    public boolean verificarCuenta(Long token) {
        // 1. Buscar el registro en verificar por token
        Verificar verificar = verificarRepository.findById(token).orElse(null);

        if (verificar == null) {
            return false; // Token no encontrado
        }

        //Obtener el cliente y actualizar Verificacion = 'SI'
        Cliente cliente = verificar.getCliente();
        cliente.setVerificacion("SI");
        clienteRepository.save(cliente);

        // 3. Borrar el token de la tabla verificar
        verificarRepository.delete(verificar);

        return true;
    }

    @Transactional
    public boolean modificarPassword(String email, String contrasena) {
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);

        if (cliente == null) {
            return false;
        }

        cliente.setContrasena(passwordEncoder.encode(contrasena));
        clienteRepository.save(cliente);

        return true;
    }

    @Transactional
    public Long guardarTokenResetPassword(Cliente cliente, Long token) {

        Restablecer restablecer = new Restablecer();
        restablecer.setId(token);
        restablecer.setCliente(cliente);

        restablecerRepository.save(restablecer);

        return token;
    }

    @Transactional
    public Long eliminarTokenResetPassword(Long token) {

        // Buscar el token en la BD
        Restablecer restablecer = restablecerRepository.findById(token)
                .orElseThrow(() -> new RuntimeException("Token no existe o ya fue usado"));

        // Guardar el ID del cliente antes de eliminar
        Long idCliente = restablecer.getCliente().getId();

        // Borrar el token
        restablecerRepository.delete(restablecer);

        // Devolver ID del cliente
        return idCliente;
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO getClienteCompleto(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró el cliente"));

        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellidoPaterno(cliente.getApellidoPaterno());
        dto.setApellidoMaterno(cliente.getApellidoMaterno());
        dto.setApodo(cliente.getApodo());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setRfc(cliente.getRfc());
        dto.setCalle(cliente.getCalle());
        dto.setNumeroExterior(cliente.getNumeroExterior());
        dto.setNumeroInterior(cliente.getNumeroInterior());
        dto.setCiudad(cliente.getCiudad());
        dto.setEstado(cliente.getEstado());
        dto.setCodigoPostal(cliente.getCodigoPostal());
        dto.setPais(cliente.getPais());
        dto.setInstruccionesExtras(cliente.getInstruccionesExtras());
        dto.setPermisos(cliente.getPermisos());
        dto.setVerificacion(cliente.getVerificacion());

        return dto;
    }
}