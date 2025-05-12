package br.com.fiap.motoflow.model;

import br.com.fiap.motoflow.infra.persistence.converter.BooleanSimNaoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_mtf_posicao_patio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosicaoPatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_posicao")
    private Long id;

    @NotNull
    @Column(name = "nr_posicao_vertical", nullable = false)
    private int posicaoVertical;

    @NotNull
    @Column(name = "nr_posicao_horizontal", nullable = false, length = 5)
    private String posicaoHorizontal;

    @Column(name = "fl_posicao_livre", nullable = false, length = 1)
    @Convert(converter = BooleanSimNaoConverter.class)
    private boolean isPosicaoLivre;

    @ManyToOne
    @JoinColumn(name = "t_mtf_patio_cd_id_patio", nullable = false)
    private Patio patio;

    @OneToOne
    @JoinColumn(name = "t_mtf_moto_cd_id_moto", unique = true, nullable = false)
    private Moto moto;
}

