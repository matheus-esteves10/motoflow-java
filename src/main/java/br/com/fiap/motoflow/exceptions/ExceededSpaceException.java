package br.com.fiap.motoflow.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExceededSpaceException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Número máximo de posições atingido no pátio %s. Capacidade máxima: %d motos";

    private final String apelidoPatio;
    private final int capacidade;

    public ExceededSpaceException(String apelidoPatio, int capacidade) {
        super(String.format(DEFAULT_MESSAGE, apelidoPatio, capacidade));
        this.apelidoPatio = apelidoPatio;
        this.capacidade = capacidade;
    }
}
