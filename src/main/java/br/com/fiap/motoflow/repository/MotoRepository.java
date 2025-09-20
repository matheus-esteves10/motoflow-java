package br.com.fiap.motoflow.repository;

import br.com.fiap.motoflow.model.Moto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByPlaca(String placa);

    Optional<Moto> findByCodRastreador(String codRastreador);

    @Query("SELECT m FROM Moto m WHERE m.tipoMoto = :tipoMoto AND m.patioId = :patioId ORDER BY m.dataEntrada ASC")
    Optional<Moto> findOldestMotoByTipoAndPatioId(@Param("tipoMoto") TipoMoto tipoMoto, @Param("patioId") Long patioId);

    @Query("SELECT m FROM Moto m WHERE " +
           "(:placa IS NULL OR m.placa = :placa) AND " +
           "(:codRastreador IS NULL OR m.codRastreador = :codRastreador)")
    Optional<Moto> findByPlacaOrCodRastreador(@Param("placa") String placa, @Param("codRastreador") String codRastreador);
}
