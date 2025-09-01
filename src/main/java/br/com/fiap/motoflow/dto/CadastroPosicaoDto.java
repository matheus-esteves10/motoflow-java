package br.com.fiap.motoflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroPosicaoDto(@NotNull
                                 @Positive
                                 Integer posicaoVerticalMax,

                                 @NotBlank
                                 String posicaoHorizontal,


                                 @NotNull
                                 Long idPatio) {
}
