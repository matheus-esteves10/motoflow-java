package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosicaoPatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patio_id")
    private Patio patio;

    @NotNull
    private long posicaoVertical;

    @NotNull
    private long posicaoHorizontal;

    private boolean isPosicaoLivre;

    @OneToOne
    private Moto moto;
}
