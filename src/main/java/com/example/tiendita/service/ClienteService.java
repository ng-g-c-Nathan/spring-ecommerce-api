package com.example.tiendita.service;

import com.example.tiendita.DTO.ActualizarPersonaRequest;
import com.example.tiendita.DTO.ClienteRegisterRequest;
import com.example.tiendita.domain.Carrito;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.domain.ListaDeseos;
import com.example.tiendita.repository.CarritoRepository;
import com.example.tiendita.repository.ClienteRepository;
import com.example.tiendita.repository.ListaDeseosRepository;
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

    public ClienteService(ClienteRepository clienteRepository, CarritoRepository carritoRepository, ListaDeseosRepository listaDeseosRepository,BCryptPasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.carritoRepository = carritoRepository;
        this.listaDeseosRepository = listaDeseosRepository;
        this.passwordEncoder = passwordEncoder;
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

    public boolean emailExiste(String email){
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

        boolean correoCambio = !correoActual.equals(req.getCorreoNuevo());

        cliente.setNombre(req.getNombre());
        cliente.setApellidoPaterno(req.getApellidoP());
        cliente.setApellidoMaterno(req.getApellidoM());
        cliente.setApodo(req.getApodo());
        cliente.setCalle(req.getCalle());
        cliente.setNumeroExterior(req.getNumeroExterior());
        cliente.setNumeroInterior(req.getNumeroInterior());
        cliente.setCiudad(req.getCiudad());
        cliente.setEstado(req.getEstado());
        cliente.setCodigoPostal(req.getCodigoPostal());
        cliente.setPais(req.getPais());
        cliente.setInstruccionesExtras(req.getInstruccionesExtras());
        cliente.setTelefono(req.getTelefono());
        String correoFinal = req.getCorreoNuevo() != null ? req.getCorreoNuevo() : correoActual;
        cliente.setEmail(correoFinal);

        if (correoCambio) {
            cliente.setVerificacion("NO");
        } else {
            cliente.setVerificacion("SI");
        }

        clienteRepository.save(cliente);

        if (correoCambio) {
            //reactivarCuenta(cliente); Aun no la agrego pero es quien se encarga de mandar correo de que cambio el correo y verifique
        }
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
}