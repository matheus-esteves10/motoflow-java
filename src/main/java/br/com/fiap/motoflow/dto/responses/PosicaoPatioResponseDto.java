package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.PosicaoPatio;

public record PosicaoPatioResponseDto(
        int posicaoVertical,
        String posicaoHorizontal
) {

    public static PosicaoPatioResponseDto fromEntity(PosicaoPatio posicao) {
        return new PosicaoPatioResponseDto(
                posicao.getPosicaoVertical(),
                posicao.getPosicaoHorizontal()
        );
    }
}
