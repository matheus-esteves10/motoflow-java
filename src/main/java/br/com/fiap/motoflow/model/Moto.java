package br.com.fiap.motoflow.model;

import br.com.fiap.motoflow.exceptions.InvalidYearException;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de moto é obrigatório.")
    private TipoMoto tipoMoto;

    @Min(value = 2012, message = "O ano deve ser maior ou igual a 2012.")
    private int ano;

    @NotBlank(message = "A placa é obrigatória.")
    @Pattern(regexp = "^[A-Z]{3}-[0-9]{4}$", message = "A placa deve estar no formato AAA-0000.")
    private String placa;

    @Positive(message = "O preço de aluguel deve ser um valor positivo.")
    private double precoAluguel;

    private boolean isAlugada;

    private LocalDate dataAlocacao;

    @OneToOne
    private PosicaoPatio posicao;


    public Moto(Long id, TipoMoto tipoMoto, int ano, String placa, double precoAluguel, boolean isAlugada, PosicaoPatio posicao) {
        this.id = id;
        this.tipoMoto = tipoMoto;
        validaAnoMax(ano);
        this.ano = ano;
        this.placa = placa;
        this.precoAluguel = precoAluguel;
        this.isAlugada = isAlugada;
        this.posicao = posicao;
    }

    private void validaAnoMax(int ano) {
        int anoMax = LocalDate.now().getYear();

        if (ano > anoMax) {
            throw new InvalidYearException("O ano não pode ser maior que o ano atual: " + anoMax);
        }
    }
}
