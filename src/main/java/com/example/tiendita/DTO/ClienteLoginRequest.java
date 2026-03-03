package com.example.tiendita.DTO;

public class ClienteLoginRequest {
    private String email;
    private String contrasena;
    public ClienteLoginRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
