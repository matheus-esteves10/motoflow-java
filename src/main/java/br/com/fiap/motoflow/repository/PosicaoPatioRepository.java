package br.com.fiap.motoflow.repository;

import br.com.fiap.motoflow.model.SetorPatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PosicaoPatioRepository extends JpaRepository<SetorPatio, Long> {



    @Query("SELECT COUNT(p) FROM SetorPatio p WHERE p.patio.id = :patioId")
    int countByPatioId(@Param("patioId") Long patioId);

    @Query("SELECT DISTINCT p.setor FROM SetorPatio p WHERE p.patio.id = :patioId")
    Optional<List<String>> posicoesHorizontais(@Param("patioId") Long patioId);


    @Query("SELECT p FROM SetorPatio p WHERE p.patio.id = :patioId AND p.setor = :posicaoHorizontal AND p.moto IS NOT NULL")
    List<SetorPatio> findAllByPatioIdAndPosicaoHorizontal(@Param("patioId") Long patioId, @Param("posicaoHorizontal") String posicaoHorizontal);

    @Query("SELECT COUNT(p) FROM SetorPatio p WHERE p.patio.id = :patioId AND p.setor = :setor")
    int countByPatioIdAndHorizontal(@Param("patioId") Long patioId, @Param("setor") String setor);

    @Query("""
        SELECT CASE WHEN COUNT(p) >= MAX(p.capacidadeSetor) THEN true ELSE false END
        FROM SetorPatio p
        WHERE p.patio.id = :patioId AND p.setor = :setor AND p.moto IS NOT NULL
    """)
    boolean setorComCapacidadeExcedida(@Param("patioId") Long patioId, @Param("setor") String setor);
}
