package com.jrcg.act4_t4.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrcg.act4_t4.dtos.EscuelaDTO;
import com.jrcg.act4_t4.models.Escuela;
import com.jrcg.act4_t4.repositories.EscuelaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/escuelas")
public class EscuelaController {

    @Autowired
    private EscuelaRepository escuelaRepository;

    // 1. OBTENER TODAS (GET /api/escuelas) -> 200 OK
    @GetMapping
    public ResponseEntity<List<Escuela>> getAllEscuelas() {
        return ResponseEntity.ok(escuelaRepository.findAll());
    }

    // 2. OBTENER POR ID (GET /api/escuelas/{id}) -> 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Escuela> getEscuelaById(@PathVariable Long id) {
        Optional<Escuela> escuela = escuelaRepository.findById(id);
        
        if (escuela.isPresent()) {
            return ResponseEntity.ok(escuela.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404
        }
    }

    // 3. CREAR NUEVA (POST /api/escuelas) -> 201 Created
    // Usamos @Valid para que Spring revise que vengan el nombre y el cct (las reglas del DTO)
    @PostMapping
    public ResponseEntity<Escuela> createEscuela(@Valid @RequestBody EscuelaDTO escuelaDTO) {
        Escuela nuevaEscuela = new Escuela(
                escuelaDTO.getNombre(),
                escuelaDTO.getCct(),
                escuelaDTO.getDireccion()
        );
        Escuela escuelaGuardada = escuelaRepository.save(nuevaEscuela);
        return new ResponseEntity<>(escuelaGuardada, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR (PUT /api/escuelas/{id}) -> 200 OK o 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Escuela> updateEscuela(@PathVariable Long id, @Valid @RequestBody EscuelaDTO escuelaDTO) {
        Optional<Escuela> escuelaExistente = escuelaRepository.findById(id);

        if (escuelaExistente.isPresent()) {
            Escuela escuelaAActualizar = escuelaExistente.get();
            escuelaAActualizar.setNombre(escuelaDTO.getNombre());
            escuelaAActualizar.setCct(escuelaDTO.getCct());
            escuelaAActualizar.setDireccion(escuelaDTO.getDireccion());
            
            return ResponseEntity.ok(escuelaRepository.save(escuelaAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR (DELETE /api/escuelas/{id}) -> 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEscuela(@PathVariable Long id) {
        if (escuelaRepository.existsById(id)) {
            escuelaRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Devuelve 204 sin cuerpo
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}