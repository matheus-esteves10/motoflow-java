package br.com.fiap.motoflow.dto.responses;

import java.util.List;

public record SetorDto(String setor,
                       Integer vagasTotais,
                       Integer ocupadas,
                       Integer disponiveis,
                       List<MotoResponseDto> motos) {
}
