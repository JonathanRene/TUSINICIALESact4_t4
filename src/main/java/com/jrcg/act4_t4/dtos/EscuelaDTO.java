package com.jrcg.act4_t4.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EscuelaDTO {

    @NotBlank(message = "El nombre de la escuela es obligatorio")
    private String nombre;

    @NotBlank(message = "La clave CCT es obligatoria")
    @Size(min = 5, max = 15, message = "La clave CCT debe tener entre 5 y 15 caracteres")
    private String cct;

    private String direccion;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCct() { return cct; }
    public void setCct(String cct) { this.cct = cct; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}