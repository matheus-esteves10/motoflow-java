package br.com.fiap.motoflow.model;

import br.com.fiap.motoflow.dto.MotoDto;
import br.com.fiap.motoflow.exceptions.InvalidYearException;
import br.com.fiap.motoflow.model.enums.StatusMoto;
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
@Data
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

    @Column(name = "nm_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMoto statusMoto;

    @Column(name = "dt_aluguel")
    private LocalDate dataAluguel;

    public Moto() {
    }

    public Moto(TipoMoto tipoMoto, int ano, String placa, BigDecimal precoAluguel, StatusMoto statusMoto, LocalDate dataAluguel) {
        this.tipoMoto = tipoMoto;
        validaAnoMax(ano);
        this.ano = ano;
        this.placa = placa;
        this.precoAluguel = precoAluguel;
        this.statusMoto = statusMoto;
        this.dataAluguel = dataAluguel;
    }

    private void validaAnoMax(int ano) {
        int anoMax = LocalDate.now().getYear();
        if (ano > anoMax) {
            throw new InvalidYearException("O ano não pode ser maior que o ano atual: " + anoMax);
        }
    }

    public static Moto from(MotoDto dto) {
        return new Moto(
                dto.tipoMoto(),
                dto.ano(),
                dto.placa(),
                dto.precoAluguel(),
                dto.statusMoto(),
                dto.dataAlocacao()
        );
    }


}

