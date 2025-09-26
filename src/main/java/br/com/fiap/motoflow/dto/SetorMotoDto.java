package br.com.fiap.motoflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SetorMotoDto(
        @Pattern(
                regexp = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$",
                message = "A placa deve estar no formato AAA0000 ou Mercosul AAA0A00."
        )
        String placa,

        @NotBlank(message = "O setor não pode estar em branco.")
        String setor,

        String codRastreador) {
}
