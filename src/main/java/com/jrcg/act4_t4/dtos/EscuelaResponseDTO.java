package com.jrcg.act4_t4.dtos;

public class EscuelaResponseDTO {

    private Long id;
    private String nombre;
    private String cct;
    private String direccion;

    public EscuelaResponseDTO(Long id, String nombre, String cct, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.cct = cct;
        this.direccion = direccion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCct() { return cct; }
    public void setCct(String cct) { this.cct = cct; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}