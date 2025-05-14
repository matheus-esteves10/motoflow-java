package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.Endereco;
import br.com.fiap.motoflow.model.Moto;
import br.com.fiap.motoflow.model.PosicaoPatio;
import br.com.fiap.motoflow.model.enums.TipoMoto;

import java.math.BigDecimal;

public record PosicaoMotoResponse(
        String placa,
        TipoMoto tipoMoto,
        int ano,
        BigDecimal precoAluguel,
        Long idPatio,
        String apelidoPatio,
        Endereco endereco,
        int posicaoVertical,
        String posicaoHorizontal,
        boolean posicaoLivre
) {
    public PosicaoMotoResponse(Moto moto, PosicaoPatio posicao) {
        this(
                moto.getPlaca(),
                moto.getTipoMoto(),
                moto.getAno(),
                moto.getPrecoAluguel(),
                posicao.getPatio().getId(),
                posicao.getPatio().getApelido(),
                posicao.getPatio().getEndereco(),
                posicao.getPosicaoVertical(),
                posicao.getPosicaoHorizontal(),
                posicao.isPosicaoLivre()
        );
    }
}

