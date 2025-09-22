package br.com.fiap.motoflow.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusMoto {
    ALUGADA,
    DISPONIVEL,
    MANUTENCAO;

    @JsonCreator
    public static StatusMoto fromString(String value) {
        return StatusMoto.valueOf(value.toUpperCase());
    }
}
