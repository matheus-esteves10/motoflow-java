package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_mtf_endereco")
@Data
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

}
