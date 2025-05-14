package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "t_mtf_operador")

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

    public Operador(Long id, String nome, String senha, Patio patio) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.patio = patio;
    }

    public Operador() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Patio getPatio() {
        return patio;
    }

    public void setPatio(Patio patio) {
        this.patio = patio;
    }
}

