package br.com.fiap.motoflow.repository;

import br.com.fiap.motoflow.model.SetorPatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SetorPatioRepository extends JpaRepository<SetorPatio, Long> {

    @Query("""
        SELECT CASE WHEN SIZE(s.motos) >= s.capacidadeSetor THEN true ELSE false END
        FROM SetorPatio s
        WHERE s.patio.id = :patioId AND s.setor = :setor
    """)
    boolean setorComCapacidadeExcedida(@Param("patioId") Long patioId, @Param("setor") String setor);

    @Query("""
        SELECT p FROM SetorPatio p
        WHERE p.setor = :setor AND p.patio.id = :patioId
    """)
    Optional<SetorPatio> findSetor(@Param("setor") String setor, @Param("patioId") Long patioId);

    @Query("""
        SELECT m.setorPatio FROM Moto m
        WHERE m.placa = :placa
    """)
    Optional<SetorPatio> findByMotoPlaca(@Param("placa") String placa);

    List<SetorPatio> findAllByPatioId(Long patioId);
}
