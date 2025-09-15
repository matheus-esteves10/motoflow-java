package br.com.fiap.motoflow.dto.refactor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SetorMotoDto(
        @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$", message = "A placa deve estar no formato AAA0000.")
        String placa,
        @NotBlank(message = "O setor não pode estar em branco.")
        String setor) {
}
