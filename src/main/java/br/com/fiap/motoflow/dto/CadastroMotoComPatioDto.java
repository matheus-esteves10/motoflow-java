package br.com.fiap.motoflow.dto;

import br.com.fiap.motoflow.model.enums.TipoMoto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CadastroMotoComPatioDto(
        TipoMoto tipoMoto,
        int ano,
        String placa,
        BigDecimal precoAluguel,
        boolean isAlugada,
        LocalDate dataAlocacao,
        Long idPatio
) {}
