package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;

public record MotoResponseDto(Long id,
                              String placa,
                              TipoMoto tipoMoto,
                              int ano,
                              StatusMoto statusMoto) {
}
