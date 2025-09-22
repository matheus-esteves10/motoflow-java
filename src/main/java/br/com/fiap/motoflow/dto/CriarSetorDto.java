package br.com.fiap.motoflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarSetorDto(
        @NotBlank String setor,
        @NotNull Integer capacidadeSetor
) {
}
