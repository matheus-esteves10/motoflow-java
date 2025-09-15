package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "t_mtf_posicao_patio")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetorPatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_posicao")
    private Long id;

    @NotNull
    @Column(name = "nr_posicao_horizontal", nullable = false, length = 5)
    private String setor;

    @NotNull
    @Column(name = "nr_capacidade_setor", nullable = false)
    private int capacidadeSetor;

    @ManyToOne
    @JoinColumn(name = "cd_id_patio", nullable = false)
    private Patio patio;

    @OneToMany(mappedBy = "setorPatio")
    private List<Moto> motos;

}
