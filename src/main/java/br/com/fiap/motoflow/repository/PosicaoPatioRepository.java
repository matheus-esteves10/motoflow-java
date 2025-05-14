package br.com.fiap.motoflow.repository;

import br.com.fiap.motoflow.model.PosicaoPatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PosicaoPatioRepository extends JpaRepository<PosicaoPatio, Long> {

    @Query("""
        SELECT p FROM PosicaoPatio p
        JOIN p.moto m
        WHERE m.placa = :placa
    """)
    Optional<PosicaoPatio> findByMotoPlaca(@Param("placa") String placa);

    Optional<PosicaoPatio> findByPosicaoHorizontalAndPosicaoVerticalAndIsPosicaoLivreTrue(
            String posicaoHorizontal, int posicaoVertical
    );
}
