package br.com.fiap.motoflow.dto.refactor;

import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CadastroMotoDto(
        @NotNull @Enumerated(EnumType.STRING)
        TipoMoto tipoMoto,

        @NotNull @Min(value = 2012, message = "O ano deve ser maior ou igual a 2012.")
        int ano,

        @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$", message = "A placa deve estar no formato AAA0000.")
        String placa,

        @Positive(message = "O preço de aluguel deve ser um valor positivo.")
        BigDecimal precoAluguel,

        @Enumerated(EnumType.STRING)
        StatusMoto statusMoto,

        @NotNull
        String setor,

        int codRastreador,

        LocalDateTime dataEntrada
) {
}
