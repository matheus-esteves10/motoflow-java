package br.com.fiap.motoflow.dto.web;

import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MotoDtoWeb (
            Long id,
            @Enumerated(EnumType.STRING)
            @NotNull(message = "O tipo de moto é obrigatório.")
            TipoMoto tipoMoto,
            @Min(value = 2012, message = "O ano deve ser maior ou igual a 2012.")
            int ano,
            @NotBlank(message = "A placa é obrigatória.")
            @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$", message = "A placa deve estar no formato AAA0000.")
            String placa,
            @Positive(message = "O preço de aluguel deve ser um valor positivo.")
            BigDecimal precoAluguel,
            StatusMoto statusMoto,
            String posicaoPatio)
    {

}
