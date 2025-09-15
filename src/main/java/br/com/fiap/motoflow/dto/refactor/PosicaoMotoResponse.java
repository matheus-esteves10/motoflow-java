package br.com.fiap.motoflow.dto.refactor;

import br.com.fiap.motoflow.model.Endereco;
import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;

import java.math.BigDecimal;

public record PosicaoMotoResponse(
        String placa,
        TipoMoto tipoMoto,
        StatusMoto statusMoto,
        int ano,
        BigDecimal precoAluguel,
        Long idPatio,
        String apelidoPatio,
        Endereco endereco,
        String setor
) {
}

