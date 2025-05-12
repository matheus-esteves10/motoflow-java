package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_mtf_operador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_registro")
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(name = "nm_operador", nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Size(max = 255)
    @Column(name = "ds_senha", nullable = false, length = 255)
    private String senha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "t_mtf_patio_cd_id_patio", nullable = false)
    private Patio patio;
}

