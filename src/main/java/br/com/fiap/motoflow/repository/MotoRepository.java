package br.com.fiap.motoflow.repository;

import br.com.fiap.motoflow.model.Moto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByPlaca(String placa);

    @Query("SELECT m FROM Moto m " +
            "JOIN m.posicaoPatio p " +
            "WHERE p.patio.id = :patioId")
    Page<Moto> findByPatioId(@Param("patioId") Long patioId, Pageable pageable);

}
