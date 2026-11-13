package com.jrcg.act4_t4.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrcg.act4_t4.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Este método es crucial para la seguridad: busca al usuario por su email
    // Optional significa que puede encontrarlo o puede que no exista
    Optional<Usuario> findByEmail(String email);
    
}