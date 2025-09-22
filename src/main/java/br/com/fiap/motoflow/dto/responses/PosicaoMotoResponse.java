package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.Endereco;
import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PosicaoMotoResponse(
        String placa,
        TipoMoto tipoMoto,
        StatusMoto statusMoto,
        int ano,
        BigDecimal precoAluguel,
        Long idPatio,
        String apelidoPatio,
        Endereco endereco,
        String setor,
        String codRastreador,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataEntrada
) {
}
