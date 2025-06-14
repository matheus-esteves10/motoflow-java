package br.com.fiap.motoflow.repository;

import br.com.fiap.motoflow.model.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperadorRepository extends JpaRepository<Operador, Long> {

    Optional<Operador> findById(Long id);
}
