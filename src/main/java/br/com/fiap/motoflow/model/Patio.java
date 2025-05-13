package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_mtf_patio")
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_patio")
    private Long id;

    @Column(name = "nm_apelido_patio")
    private String apelido;

    @Column(name = "nr_capacidade")
    private Integer capacidade;

    @Column(name = "nr_area")
    private Integer area;

    @OneToOne(optional = false)
    @JoinColumn(name = "t_mtf_endereco_cd_id_endereco", referencedColumnName = "cd_id_endereco", unique = true, nullable = false)
    private Endereco endereco;

    @OneToMany(mappedBy = "patio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PosicaoPatio> posicoes;

    public Patio() {
    }

    public Patio(Long id, String apelido, Integer capacidade, Integer area, Endereco endereco, List<PosicaoPatio> posicoes) {
        this.id = id;
        this.apelido = apelido;
        this.capacidade = capacidade;
        this.area = area;
        this.endereco = endereco;
        this.posicoes = posicoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<PosicaoPatio> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(List<PosicaoPatio> posicoes) {
        this.posicoes = posicoes;
    }
}

