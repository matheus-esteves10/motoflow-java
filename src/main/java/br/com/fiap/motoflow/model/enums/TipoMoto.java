package br.com.fiap.motoflow.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoMoto {

    MOTTU_POP,
    MOTTU_SPORT,
    MOTTU_E;

    @JsonCreator
    public static TipoMoto fromString(String value) {
        return TipoMoto.valueOf(value.toUpperCase());
    }
}
