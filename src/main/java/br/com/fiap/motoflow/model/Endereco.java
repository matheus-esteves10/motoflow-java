package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

@Table(name = "t_mtf_endereco")

public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_endereco", nullable = false)
    private Long id;

    @Column(name = "nm_logradouro", nullable = false)
    private String logradouro;

    @Column(name = "nr_endereco")
    private int numero;

    @Column(name = "nm_bairro")
    private String bairro;

    @Column(name = "nm_cidade", nullable = false)
    private String cidade;

    @Column(name = "sg_estado")
    private String siglaEstado;

    @Column(name = "nr_cep", nullable = false)
    private String cep;
}
