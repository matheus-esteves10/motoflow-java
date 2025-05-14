package br.com.fiap.motoflow.model;

import br.com.fiap.motoflow.exceptions.InvalidYearException;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_mtf_moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_moto")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de moto é obrigatório.")
    @Column(name = "nm_tipo", length = 30, nullable = false)
    private TipoMoto tipoMoto;

    @Min(value = 2012, message = "O ano deve ser maior ou igual a 2012.")
    @Column(name = "nr_ano", nullable = false)
    private int ano;

    @NotBlank(message = "A placa é obrigatória.")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$", message = "A placa deve estar no formato AAA0000.")
    @Column(name = "nr_placa", length = 7, nullable = false)
    private String placa;

    @Positive(message = "O preço de aluguel deve ser um valor positivo.")
    @Column(name = "nr_preco_aluguel", precision = 8, scale = 2)
    private BigDecimal precoAluguel;

    @Column(name = "fl_alugada", nullable = false, length = 1)
    private boolean isAlugada;

    @Column(name = "dt_alocacao")
    private LocalDate dataAlocacao;

    public Moto() {
    }

    public Moto(TipoMoto tipoMoto, int ano, String placa, BigDecimal precoAluguel, boolean isAlugada, LocalDate dataAlocacao) {
        this.tipoMoto = tipoMoto;
        validaAnoMax(ano);
        this.ano = ano;
        this.placa = placa;
        this.precoAluguel = precoAluguel;
        this.isAlugada = isAlugada;
        this.dataAlocacao = dataAlocacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMoto getTipoMoto() {
        return tipoMoto;
    }

    public void setTipoMoto(TipoMoto tipoMoto) {
        this.tipoMoto = tipoMoto;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getPrecoAluguel() {
        return precoAluguel;
    }

    public void setPrecoAluguel(BigDecimal precoAluguel) {
        this.precoAluguel = precoAluguel;
    }

    public boolean isAlugada() {
        return isAlugada;
    }

    public void setAlugada(boolean alugada) {
        isAlugada = alugada;
    }

    public LocalDate getDataAlocacao() {
        return dataAlocacao;
    }

    public void setDataAlocacao(LocalDate dataAlocacao) {
        this.dataAlocacao = dataAlocacao;
    }

    private void validaAnoMax(int ano) {
        int anoMax = LocalDate.now().getYear();
        if (ano > anoMax) {
            throw new InvalidYearException("O ano não pode ser maior que o ano atual: " + anoMax);
        }
    }

}

