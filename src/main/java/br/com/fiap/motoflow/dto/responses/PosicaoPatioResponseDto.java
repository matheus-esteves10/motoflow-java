package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.SetorPatio;

public record PosicaoPatioResponseDto(
        int posicaoVertical,
        String posicaoHorizontal
) {

    public static PosicaoPatioResponseDto fromEntity(SetorPatio posicao) {
        return new PosicaoPatioResponseDto(
                posicao.getPosicaoVertical(),
                posicao.getPosicaoHorizontal()
        );
    }
}
