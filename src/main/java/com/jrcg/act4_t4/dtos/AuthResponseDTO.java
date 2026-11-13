package com.jrcg.act4_t4.dtos;

public class AuthResponseDTO {
    
    private String token;
    private String mensaje;

    public AuthResponseDTO(String token, String mensaje) {
        this.token = token;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}