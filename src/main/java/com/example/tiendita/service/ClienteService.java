package com.example.tiendita.service;

import com.example.tiendita.DTO.ClienteRegisterRequestDTO;
import com.example.tiendita.domain.Carrito;
import com.example.tiendita.domain.Cliente;
import com.example.tiendita.domain.ListaDeseos;
import com.example.tiendita.repository.CarritoRepository;
import com.example.tiendita.repository.ClienteRepository;
import com.example.tiendita.repository.ListaDeseosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public void agregarCliente(ClienteRegisterRequestDTO dto) throws Exception {

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

}