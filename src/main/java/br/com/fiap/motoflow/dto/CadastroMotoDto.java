package br.com.fiap.motoflow.dto;

import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CadastroMotoDto(
        @NotNull @Enumerated(EnumType.STRING)
        TipoMoto tipoMoto,

        @NotNull @Min(value = 2012, message = "O ano deve ser maior ou igual a 2012.")
        int ano,

        @Pattern(
                regexp = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$",
                message = "A placa deve estar no formato AAA0000 ou Mercosul AAA0A00."
        )
        String placa,

        @Positive(message = "O preço de aluguel deve ser um valor positivo.")
        BigDecimal precoAluguel,

        @Enumerated(EnumType.STRING)
        StatusMoto statusMoto,

        @NotNull
        String setor,

        @NotBlank(message = "É necessário informar o código do Beacon colocado na moto.")
        String codRastreador,

        LocalDateTime dataEntrada
) {
}
