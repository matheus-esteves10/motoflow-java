package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String endereco;

    @OneToMany(mappedBy = "patio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PosicaoPatio> posicoes;


}
