package com.jrcg.act4_t4.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.jrcg.act4_t4.dtos.EscuelaResponseDTO;
import com.jrcg.act4_t4.models.Escuela;
import com.jrcg.act4_t4.repositories.EscuelaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/escuelas")
public class EscuelaController {

    @Autowired
    private EscuelaRepository escuelaRepository;

    // a) GET PARA LISTAR CON PAGINACIÓN (ej: /api/escuelas?page=0&size=5) -> 200 OK
    @GetMapping
    public ResponseEntity<Page<EscuelaResponseDTO>> getAllEscuelas(Pageable pageable) {
        Page<Escuela> escuelasPage = escuelaRepository.findAll(pageable);
        
        // Convertimos la entidad Page a Page de EscuelaResponseDTO
        Page<EscuelaResponseDTO> responsePage = escuelasPage.map(escuela -> new EscuelaResponseDTO(
                escuela.getId(),
                escuela.getNombre(),
                escuela.getCct(),
                escuela.getDireccion()
        ));

        return ResponseEntity.ok(responsePage);
    }

    // a) GET PARA VER UN REGISTRO INDIVIDUAL -> 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<EscuelaResponseDTO> getEscuelaById(@PathVariable Long id) {
        Optional<Escuela> escuela = escuelaRepository.findById(id);
        
        if (escuela.isPresent()) {
            Escuela e = escuela.get();
            EscuelaResponseDTO responseDTO = new EscuelaResponseDTO(e.getId(), e.getNombre(), e.getCct(), e.getDireccion());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // b) POST PARA CREAR (Con DTO y @Valid) -> 201 Created
    @PostMapping
    public ResponseEntity<EscuelaResponseDTO> createEscuela(@Valid @RequestBody EscuelaDTO escuelaDTO) {
        Escuela nuevaEscuela = new Escuela(
                escuelaDTO.getNombre(),
                escuelaDTO.getCct(),
                escuelaDTO.getDireccion()
        );
        Escuela guardada = escuelaRepository.save(nuevaEscuela);
        
        EscuelaResponseDTO responseDTO = new EscuelaResponseDTO(guardada.getId(), guardada.getNombre(), guardada.getCct(), guardada.getDireccion());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // c) PUT PARA ACTUALIZAR (Con DTO y @Valid) -> 200 OK o 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<EscuelaResponseDTO> updateEscuela(@PathVariable Long id, @Valid @RequestBody EscuelaDTO escuelaDTO) {
        Optional<Escuela> escuelaExistente = escuelaRepository.findById(id);

        if (escuelaExistente.isPresent()) {
            Escuela e = escuelaExistente.get();
            e.setNombre(escuelaDTO.getNombre());
            e.setCct(escuelaDTO.getCct());
            e.setDireccion(escuelaDTO.getDireccion());
            
            Escuela actualizada = escuelaRepository.save(e);
            EscuelaResponseDTO responseDTO = new EscuelaResponseDTO(actualizada.getId(), actualizada.getNombre(), actualizada.getCct(), actualizada.getDireccion());
            
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // d) DELETE PARA ELIMINAR -> 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEscuela(@PathVariable Long id) {
        if (escuelaRepository.existsById(id)) {
            escuelaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}