package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.Moto;
import br.com.fiap.motoflow.model.PosicaoPatio;
import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;

public record MotoResponseDto(Long id,
                              String placa,
                              TipoMoto tipoMoto,
                              int ano,
                              StatusMoto statusMoto,
                              String posicaoHorizontal,
                              Integer posicaoVertical) {

    public static MotoResponseDto from(Moto moto) {
        return new MotoResponseDto(
                moto.getId(),
                moto.getPlaca(),
                moto.getTipoMoto(),
                moto.getAno(),
                moto.getStatusMoto(),
                moto.getPosicaoPatio().getPosicaoHorizontal(),
                moto.getPosicaoPatio().getPosicaoVertical()
        );
    }
}
