package br.com.fiap.motoflow.dto.responses;

import java.util.List;

public record MotoHorizontalDto(String posicaoHorizontal,
                                Integer vagasTotais,
                                List<MotoResponseDto> motos) {
}
