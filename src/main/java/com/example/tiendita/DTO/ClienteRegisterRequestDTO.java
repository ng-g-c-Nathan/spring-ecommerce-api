package com.example.tiendita.DTO;

public class ClienteRegisterRequestDTO {

    private String email;
    private String contrasena;

    public ClienteRegisterRequestDTO() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String Contrasena) { this.contrasena = Contrasena; }
}