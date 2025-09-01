package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.PosicaoPatio;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CadastroPosicaoResponseDto(
        Set<PosicaoPatioResponseDto> novasPosicoes,
        String mensagem
) {}
