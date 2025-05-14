package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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

    public Endereco() {
    }

    public Endereco(Long id, String logradouro, int numero, String bairro, String cidade, String siglaEstado, String cep) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.siglaEstado = siglaEstado;
        this.cep = cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
