package br.com.fiap.motoflow.repository;

import br.com.fiap.motoflow.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatioRepository extends JpaRepository<Patio, Long> {

    static final String COUNT_POSICOES_DISPONIVEIS = "SELECT COUNT(p) FROM PosicaoPatio p WHERE p.patio.id = :patioId AND p.isPosicaoLivre = true";
    static final String COUNT_POSICOES_OCUPADAS = "SELECT COUNT(p) FROM PosicaoPatio p WHERE p.patio.id = :patioId AND p.isPosicaoLivre = false";

    @Query(COUNT_POSICOES_DISPONIVEIS)
    int countPosicoesDisponiveis(@Param("patioId") Long patioId);

    @Query(COUNT_POSICOES_OCUPADAS)
    int countPosicoesOcupadas(@Param("patioId") Long patioId);
}
