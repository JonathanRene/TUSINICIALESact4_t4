package com.jrcg.act4_t4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrcg.act4_t4.models.Escuela;

@Repository
public interface EscuelaRepository extends JpaRepository<Escuela, Long> {
    // Spring Data JPA ya nos regala save(), findAll(), findById() y deleteById()
}