package br.com.fiap.motoflow.model;

import br.com.fiap.motoflow.exceptions.InvalidYearException;
import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_mtf_moto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Pattern(
            regexp = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$",
            message = "A placa deve estar no formato AAA0000 ou Mercosul AAA0A00."
    )
    @Column(name = "nr_placa", length = 7)
    private String placa;

    @Positive(message = "O preço de aluguel deve ser um valor positivo.")
    @Column(name = "nr_preco_aluguel", precision = 8, scale = 2)
    private BigDecimal precoAluguel;

    @Column(name = "nm_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMoto statusMoto;

    @Column(name = "dt_alocacao")
    private LocalDate dataAluguel;

    @Column(name = "nr_cod_rastreador", unique = true)
    private String codRastreador;

    @Column(name = "dt_entrada")
    private LocalDateTime dataEntrada;

    @ManyToOne
    @JoinColumn(name = "cd_id_setor")
    private SetorPatio setorPatio;

    @Column(name = "cd_id_patio")
    private Long patioId;


    public Moto(TipoMoto tipoMoto, int ano, String placa, BigDecimal precoAluguel, StatusMoto statusMoto, LocalDate dataAluguel, String codRastreador, LocalDateTime dataEntrada) {
        this.tipoMoto = tipoMoto;
        validaAnoMax(ano);
        this.ano = ano;
        this.placa = placa;
        this.precoAluguel = precoAluguel;
        this.statusMoto = statusMoto;
        this.dataAluguel = dataAluguel;
        this.codRastreador = codRastreador;
        this.dataEntrada = dataEntrada != null ? dataEntrada : LocalDateTime.now();
    }

    private void validaAnoMax(int ano) {
        int anoMax = LocalDate.now().getYear();
        if (ano > anoMax) {
            throw new InvalidYearException("O ano não pode ser maior que o ano atual: " + anoMax);
        }
    }

}
