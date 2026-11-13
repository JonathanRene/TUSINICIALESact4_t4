package com.jrcg.act4_t4.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequestDTO {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe tener un formato de correo electrónico válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}