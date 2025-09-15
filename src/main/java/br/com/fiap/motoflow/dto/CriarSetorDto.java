package br.com.fiap.motoflow.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarSetorDto(
        @NotBlank String setor,
        @NotBlank Integer capacidadeSetor
) {
}
